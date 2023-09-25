# Test document for the Document class

## Method: constructor, getFilePath(), getFileName(), getFileContent(), isSaved(),

Equivalence classes:

1. No file
1. File doesn't exist
1. Valid file


Test Case | Test value | Covered equivalence class | Expected test results
-------- | -------- | --------|-----| 
1 | No file | 1 | No errors |
2 | File not found | 2 | Throws a FileNotFoundException |
3 | File containing plain text | 3 | No Errors (reading from file) |

## Method: formatText(StyledDocument doc, Tag tag, int selStart, int selEnd)

Equivalence classes:

1. Valid parameters
2. Invalid Parameters



Test Case | Test value | Covered equivalence class | Expected test results
-------- | -------- | --------|-----| 
1 | StyledDocument docText | 2 | No Changes|
2 | StyledDocument docText | 1| docText|

## Method: formatTextFont(StyledDocument doc, int selStart, int selEnd, int fontSize, String fontName)

Equivalence classes:

1. Valid parameters
2. Invalid Parameters



Test Case | Test value | Covered equivalence class | Expected test results
-------- | -------- | --------|-----| 
1 | StyledDocument docText | 2 | No Changes|
2 | StyledDocument docText | 1| docText|

## Method: saveDocument()

Equivalence classes:

1. Valid document



Test Case | Test value | Covered equivalence class | Expected test results
-------- | -------- | --------|-----| 
1 | File file | 1 | File got saved with the correct content|

## Method: updateFormattingList()

Equivalence classes:

1. Valid content
2. Invalid content



Test Case | Test value | Covered equivalence class | Expected test results
-------- | -------- | --------|-----| 
1 | List<ParsedContent> list | 1 | Same content as list|
2 | Boolean thrown | 2| Throws an DamagedFileException|

