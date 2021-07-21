(ns scramble.server-test
  (:require [clojure.test :refer [deftest testing is are run-tests]]
            [scramble.server :as x]))


(deftest scramble-test
  (testing "should return true, if a portion of str1 characters can be rearranged to match str2"
    (are [str1 str2] (is (x/scramble? str1 str2))
      "rekqodlw" "world"
      "rekqodlww" "world"
      "cedewaraaossoqqyt" "codewars"
      "cedewaraaossoqqyt" ""
      "rekqodlw" nil))
  
  (testing "should return false, if a portion of str1 characters can't be rearranged to match str2"
    (are [str1 str2] (is (not (x/scramble? str1 str2)))
      "katas" "steak"
      "" "steak"
      nil "steak"
      "rekqodlw" "wworld")))



(comment

  (run-tests)

  (comment))

