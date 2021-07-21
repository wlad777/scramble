## Scramblies challenge

### Task 1
Complete the function (scramble str1 str2) that returns true if a portion of str1 characters can be rearranged to match str2, otherwise returns false

#### Notes:
Only lower case letters will be used (a-z). No punctuation or digits will be included.
Performance needs to be considered

Examples:

    (scramble? “rekqodlw” ”world”) ==> true
    (scramble? “cedewaraaossoqqyt” ”codewars”) ==> true
    (scramble? “katas”  “steak”) ==> false

### Task 2
Create a web service that accepts two strings in a request and applies function scramble? from previous task to them.



### Usage

Start server:

    lein repl
    (start-app) 


Use API:

    curl localhost:9090/scramble/abc/cab


### Testing

    lein midje :autotest

