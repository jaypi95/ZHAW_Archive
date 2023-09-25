# Test document for the MarkDownWriter class

## Method: getMarkDownText()

Equivalence classes:

1. Font Tags
2. Style Tags
3. No Tags and no Style

Test Case | Test value | Covered equivalence class | Expected test results
-------- | -------- | --------|-----| 
1   | Tag.Bold, Tag.Cursive   | 2 |*String expectedResult|
2   | Tag.Fontend, Tag.Fontstart(Fontfam Papyrus, size 8)   | 1 |*String expectedResult|
3   | Tag.Fontstart, Tag.Fontend, Tag.Bold, Tag.Cursive   | 1 and 2 |*String expectedResult|
4   | Empty String   | 3 |""|
5   | Tag.Fontstart, Tag.Fontend, Tag.Bold, Tag.Underlinestart, Tag.Underlineend, Tag.Strikethrough, Tag.Monospace   | 1 and 2 |*String expectedResult|

*The String expectedResult can be looked up in the test class.
This string is made from the markdown syntax and is aligned to match the priority of the tags. 
