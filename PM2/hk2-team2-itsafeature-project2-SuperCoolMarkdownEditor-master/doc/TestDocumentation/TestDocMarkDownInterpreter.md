# Test document for the MarkDownIntepreter class
## Method: interpretMarkdown(String content)

Equivalence classes:

1. Valid file
1. Invalid file
1. Empty file


Test Case | Test value | Covered equivalence class | Expected test results
-------- | -------- | --------|-----| 
1   | Valid file, containing multiple tags   | 1 | Returns List equals manually set up Formatting list|
2   | Invalid file, missing a closing tag, including multiple other tags   | 2 |Throws DamagedFileException|
3   | File containing an empty String   | 3 |Returns an empty List of formats|
4   | Valid file, containing no tags (unformatted)   | 1 |Returns an empty List of formats|
5   | Valid file, containing FONT tags| 1 |Returns List equals manually set up Formatting list|
6   | Valid file, containing an html style tag similar to FONT tag| 1 |Returns an empty List of formats|
7   | Valid file, containing an odd number of asymmetric tags| 1 |Returns List equals manually set up Formatting list|

## Method: removeAllMarkdownTags(String markdownContent)

Equivalence classes:

1. Valid file
1. Invalid file
1. Empty file


Test Case | Test value | Covered equivalence class | Expected test results
-------- | -------- | --------|-----| 
1   | Valid file, containing multiple tags   | 1 | Returns text without any markdown tags in it|
2   | Invalid file, containing multiple tags but one missing starting tag   | 2 | Returns text without any markdown tags in it|
3   | Empty file   | 3 | Returns empty String|
4   | Valid file, containing an html style tag similar to FONT tag   | 1 | Returns text without any markdown tags in it but leaves the unknown tag untouched|
