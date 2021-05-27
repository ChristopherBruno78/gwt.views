package com.edusoftwerks.gwt.views.client.ui.text;

import com.edusoftwerks.gwt.views.client.dom.DOM;
import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.dom.KeyboardEventListener;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.shared.geometry.Range;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Timer;
import elemental2.dom.Event;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.KeyboardEvent;
import jsinterop.base.Js;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MaskedText extends Text {

    private static final Map<String, String> definitions;

    static {
        definitions = new HashMap<>();
        definitions.put("9", "[0-9]");
        definitions.put("a", "[A-Za-z]");
        definitions.put("*", "[A-Za-z0-9]");
        Theme.get().TextCss().ensureInjected();
    }

    private List<RegExp> tests;
    private List<String> buffer;
    private String defaultBuffer;
    private String focusText;
    private Timer caretTimer = null;
    private int len;
    private int partialPosition;
    private int firstNonMaskPos = -1;

    public MaskedText(TextProps props) {
        super(props);
    }

    @Override
    public void didEnterDocument() {
        super.didEnterDocument();
        parseMask();
    }

    @Override
    protected DOMElement renderControl() {
        assert (!this.props.multiline()) : "MaskedTextView does not support multiline";
        return super.renderControl();
    }

    @Override
    protected void addEventListeners() {
        addEventListener(Events.KEYDOWN, new KeyboardEventListener() {
            @Override
            public void handleKeyboardEvent(KeyboardEvent keyboardEvent, int keyCode) {
                handleKeyDown(keyboardEvent, keyCode);
            }
        });
        addEventListener(Events.KEYPRESS, new KeyboardEventListener() {
            @Override
            public void handleKeyboardEvent(KeyboardEvent keyboardEvent, int keyCode) {
                handleKeyPress(keyboardEvent, keyCode);
            }
        });
        addEventListener(Events.FOCUS, this::handleFocus);
        addEventListener(Events.BLUR, this::handleBlur);
        super.addEventListeners();
    }

    private void handleKeyDown(KeyboardEvent event, int keyCode) {
        if (this.props.readOnly()) {
            return;
        }
        int begin, end;
        Integer[] pos;
        switch (keyCode) {
            case KeyCodes.KEY_BACKSPACE:
            case KeyCodes.KEY_DELETE:
                pos = caret(-1, -1);
                begin = pos[0];
                end = pos[1];
                if (end - begin == 0) {
                    begin = (keyCode != KeyCodes.KEY_DELETE) ? seekPrev(begin) : (end = seekNext(begin - 1));
                    end = (keyCode == KeyCodes.KEY_DELETE) ? seekNext(end) : end;
                }
                clearBuffer(begin, end);
                shiftL(begin, end - 1);
                event.preventDefault();
                break;
            case KeyCodes.KEY_ENTER:
                getElement().blur();
                break;
            case KeyCodes.KEY_ESCAPE:
                setText(focusText);
                caret(0, checkVal(false));
                event.preventDefault();
                break;
        }
    }

    private void handleKeyPress(KeyboardEvent event, int keyCode) {
        int p, next = -1;
        String c;
        Integer[] pos = caret(-1, -1);
        int begin = pos[0];
        int end = pos[1];
        if (event.ctrlKey || event.altKey || event.metaKey || keyCode < 32) { // ignore
            return;
        } else {
            if (end - begin != 0) {
                clearBuffer(begin, end);
                shiftL(begin, end - 1);
            }
            p = seekNext(begin - 1);
            if (p < len) {
                c = DOM.stringFromCharCode(keyCode);
                if (tests.get(p).test(c)) {
                    shiftR(p);
                    buffer.set(p, c);
                    writeBuffer();
                    next = seekNext(p);
                }
                caret(next, -1);
            }
            event.preventDefault();
            Events.fireEvent(Events.INPUT, this);
        }
    }

    private void handleFocus(Event event) {
        if (caretTimer != null) {
            caretTimer.cancel();
        }
        final String mask = this.props.mask();
        if (mask != null) {
            focusText = getText();
            final int pos = checkVal(false);
            writeBuffer();
            caretTimer = new Timer() {
                @Override
                public void run() {
                    if (!DOM.hasFocus(input.getElement())) {
                        return;
                    }
                    if (pos == mask.replace("?", "").length()) {
                        caret(0, pos);
                    } else {
                        caret(pos, -1);
                    }
                }
            };
            caretTimer.schedule(10);
        }
    }

    private void handleBlur(Event event) {
        checkVal(false);
    }

    private Integer[] caret(int begin, int end) {
        HTMLInputElement $input = Js.cast(input.getElement());
        if (getText().length() == 0 || isHidden() || !DOM.hasFocus($input)) {
            return new Integer[] {0, 0};
        }
        if (begin > -1) {
            end = (end > -1) ? end : begin;
            setSelectionRange(begin, end);
        } else {
            Range range = getSelectionRange();
            begin = range.start;
            end = range.end;
        }
        return new Integer[] {begin, end};
    }

    private void parseMask() {
        String mask = this.props.mask();
        assert (mask != null && mask.length() > 0) : "MaskedText must be given a mask regex.";
        len = mask.length();
        partialPosition = len;
        firstNonMaskPos = -1;
        tests = new ArrayList<>();
        buffer = new ArrayList<>();
        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt(i);
            if (c == '?') {
                len--;
                partialPosition = i;
            } else if (definitions.containsKey(String.valueOf(c))) {
                tests.add(RegExp.compile(definitions.get(String.valueOf(c))));
                if (firstNonMaskPos == -1) {
                    firstNonMaskPos = tests.size() - 1;
                }

            } else {
                tests.add(null);
            }
            if (c != '?') {
                String b = definitions.containsKey(String.valueOf(c)) ? getPlaceholder(i) : String.valueOf(c);
                buffer.add(b);
            }
        }
        defaultBuffer = bufferAsString();
        focusText = getRawValue();
    }

    private int seekNext(int pos) {
        while (++pos < len && tests.get(pos) == null)
            ;
        return pos;
    }

    private int seekPrev(int pos) {
        while (--pos >= 0 && tests.get(pos) == null)
            ;
        return pos;
    }

    private String getPlaceholder(int pos) {
        if (pos < this.props.maskPlaceholder().length()) {
            return String.valueOf(this.props.maskPlaceholder().charAt(pos));
        }
        return String.valueOf(this.props.maskPlaceholder().charAt(0));
    }

    private void shiftL(int begin, int end) {
        int i, j;
        if (begin < 0) {
            return;
        }
        for (i = begin, j = seekNext(end); i < len; i++) {
            if (tests.get(i) != null) {
                if (j < len && tests.get(i).test(buffer.get(j))) {
                    buffer.set(i, buffer.get(j));
                    buffer.set(j, getPlaceholder(j));
                } else {
                    break;
                }
                j = seekNext(j);
            }
        }
        writeBuffer();
        caret(Math.max(firstNonMaskPos, begin), -1);
    }

    private void shiftR(int pos) {
        int i, j;
        String c;
        String t;
        for (i = pos, c = getPlaceholder(pos); i < len; i++) {
            if (tests.get(i) != null) {
                j = seekNext(i);
                t = buffer.get(i);
                buffer.set(i, c);
                if (j < len && tests.get(j).test(t)) {
                    c = t;
                } else {
                    break;
                }
            }
        }
    }

    private void clearBuffer(int start, int end) {
        int i;
        for (i = start; i < end && i < len; i++) {
            if (i > -1) {
                if (tests.get(i) != null) {
                    buffer.set(i, getPlaceholder(i));
                }
            }
        }
    }

    private void writeBuffer() {
        setText(bufferAsString());
    }

    private int checkVal(boolean allow) {
        String test = getText();
        int lastMatch = -1, i, pos;
        String c;
        for (i = 0, pos = 0; i < len; i++) {
            if (tests.get(i) != null) {
                buffer.set(i, getPlaceholder(i));
                while (pos++ < test.length()) {
                    c = "" + test.charAt(pos - 1);
                    if (tests.get(i).test(c)) {
                        buffer.set(i, c);
                        lastMatch = i;
                        break;
                    }
                }
                if (pos > test.length()) {
                    clearBuffer(i + 1, len);
                    break;
                }
            } else {
                if (buffer.get(i).equals(String.valueOf(test.charAt(pos)))) {
                    pos++;
                }
                if (i < partialPosition) {
                    lastMatch = i;
                }
            }
        }
        if (allow) {
            writeBuffer();
        } else if (lastMatch + 1 < partialPosition) {
            if (bufferAsString().equals(defaultBuffer)) {
                setText("");
                clearBuffer(0, len);
            } else {
                writeBuffer();
            }

        } else {
            writeBuffer();
            setText(getText().substring(0, lastMatch + 1));
        }
        return (partialPosition > 0 ? i : firstNonMaskPos);
    }

    private String bufferAsString() {
        StringBuilder bufferStr = new StringBuilder();
        for (String s : buffer) {
            bufferStr.append(s);
        }
        return bufferStr.toString();
    }
}
