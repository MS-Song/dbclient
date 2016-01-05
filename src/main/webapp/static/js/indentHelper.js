window.IndentHelper = (function () {
    var indent = function(textarea, isReversed) {
        //////////
        // variable for indent function scope
        var tabCharacter = '    ',
            source,

        //////////
        // private getter
        getSourceInformation = function(textarea) {
            var content        = textarea.value,
                selectionStart = textarea.selectionStart,
                selectionEnd   = textarea.selectionEnd,
                selectedString = content.substring(selectionStart, selectionEnd);

            return {
                element:         textarea,
                content:         content,
                selectionStart:  selectionStart,
                selectionEnd:    selectionEnd,
                selectionString: selectedString
            };
        },

        getReplaceeInformation = function() {
            var start   = source.content.lastIndexOf('\n', source.selectionStart - 1),
                end     = source.content.indexOf('\n', source.selectionEnd),
                content;

            end     = end == -1 ? source.content.length - 1 : end;
            content = source.content.substring(start, end);

            return {
                start:   start,
                end:     end,
                content: content
            };
        },

        //////////
        // helper
        replaceString = function(start, end, indentedString) {
            source.element.value = source.content.substring(0, start) + indentedString + source.content.substring(end, source.content.length);
        },

        setTextareaSelection = function(replaceStart, selectionStart, selectionEnd) {
            source.element.selectionStart = selectionStart;
            if(source.element.selectionStart < replaceStart + 1)
                source.element.selectionStart = replaceStart + 1;

            if(selectionEnd < source.element.selectionStart)
                selectionEnd = source.element.selectionStart;
            source.element.selectionEnd = selectionEnd;
        },

        //////////
        // indent or unindent functions
        indentSelectedLine = function() {
            replaceString(source.selectionStart, source.selectionEnd, tabCharacter);
            setTextareaSelection(-1, source.selectionStart + tabCharacter.length, source.selectionStart + tabCharacter.length);
        },

        unindentSelectedLine = function() {
            var removedTabCharacterLength,
                replacee = getReplaceeInformation(),
                replacedRegExp       = /(^|\n)([ ]{1,4})/;

            if(replacee.content.match(replacedRegExp) === null) {
                removedTabCharacterLength = 0;
            } else {
                removedTabCharacterLength = replacee.content.match(replacedRegExp)[2].length;
            }

            replaceString(replacee.start, replacee.end, replacee.content.replace(replacedRegExp, '$1'));
            setTextareaSelection(replacee.start, source.selectionStart - removedTabCharacterLength, source.selectionStart - removedTabCharacterLength);
        },

        indentSelectedLines = function() {
            var replacee = getReplaceeInformation(),
                replacedRegExp       = /(^|\n)(?!\n)/g;

            replaceString(replacee.start, replacee.end, replacee.content.replace(replacedRegExp, "$1"+tabCharacter));
            setTextareaSelection(replacee.start, source.selectionStart + tabCharacter.length, source.selectionEnd + (tabCharacter.length * source.selectionString.match(replacedRegExp).length));
        },

        unindentSelectedLines = function() {
            var replacee = getReplaceeInformation(),
                replacedRegExp       = /(^|\n)([ ]{1,4})/g,
                removedTabs          = replacee.content.match(replacedRegExp),
                removedTabCharacterLength = 0,
                firstTabLength            = 0,
                lastMatchOfSelectedString,
                index,
                value;

            if(removedTabs !== null) {
                lastMatchOfSelectedString = source.selectionString.match('\n[ ]{0,3}$');

                for(index = 0; index < removedTabs.length; index++) {
                    value = removedTabs[index];
                    if(index != removedTabs.length - 1 || !lastMatchOfSelectedString) {
                        removedTabCharacterLength += (value.match(/[ ]+/)[0].length);
                    } else {
                        removedTabCharacterLength += lastMatchOfSelectedString[0].length - 1;
                    }
                }

                if(source.content.substring(replacee.start, source.selectionStart).match(replacedRegExp) !== null) {
                    firstTabLength = removedTabs[0].match(/[ ]+/)[0].length;
                }
            }

            replaceString(replacee.start, replacee.end, replacee.content.replace(replacedRegExp, "$1"));
            setTextareaSelection(replacee.start, source.selectionStart - firstTabLength, source.selectionEnd - removedTabCharacterLength);
        };



        if(document.selection) {
            ////////////
            // for IE //
            ////////////

        } else {
            source = getSourceInformation(textarea);

            if(source.selectionString.match(/\n/) === null) {
                if(isReversed)
                    unindentSelectedLine();
                else
                    indentSelectedLine();

            } else {
                if(isReversed)
                    unindentSelectedLines();
                else
                    indentSelectedLines();
            }
        }
    };

    return {
        indent: indent,

        eventListener: function (event) {
            if(event !== undefined && event.keyCode == 9) {
                event.preventDefault();
                indent(this, event.shiftKey);
            }
        },

        addEventListener: function(targetElement, eventName) {
            eventName = (eventName || 'keydown');

            if(targetElement.addEventListener) {
                targetElement.addEventListener(eventName, this.eventListener, false);
            } else if(targetElement.attachEvent) {
                targetElement.attachEvent("on"+eventName, this.eventListener);
            } else {
                targetElement[eventName] = this.eventListener;
            }
        }
    };
})();