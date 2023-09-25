# Test document for the RecentFiles class

## Method: addRecentOpenedFile(Document) and getRecentOpenedFilesList()

Equivalence classes:

1. 1 File
2. 6 Files
3. 7 Files

Test Case | Test value | Covered equivalence class | Expected test results
-------- | -------- | --------|-----| 
1   | file1   | 1 |1|
2   | file(1,2,3,4,5,6)   | 2 |6|
3   | file(1,2,3,4,5,6,7)   | 3|6|

## Method: removeRecentDocument

Equivalence classes:

1. Right filepath
2. Wrong filepath


Test Case | Test value | Covered equivalence class | Expected test results
-------- | -------- | --------|-----| 
1   | doc1 | 1 |0|
2   | doc1 and doc2   | 2 |1|
