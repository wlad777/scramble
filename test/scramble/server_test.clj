(ns scramble.server-test
  (:require [clojure.test :refer [deftest testing is are]]
            [midje.sweet :refer [fact against-background before contains =>]]
            [clj-http.client :as client]
            [environ.core :refer [env]]
            [scramble.core :as core]
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


;;;;;; INTEGRATION TESTS ;;;;;;

(defn- send-request [{:keys [method path]}]
  (-> {:method           (or method :get)
       :url              (str "http://localhost:" (env :http-port) path)
       :coerce           :always
       :throw-exceptions false
       :socket-timeout   10000
       :conn-timeout     10000}
      (client/request)))


(against-background
 [(before :contents (core/init) :after (core/destroy))]

 (fact "revokes root request"
       (send-request {})
       => (contains {:status 404
                     :body   "Not Found"}))

 (fact "revokes unknown path"
       (send-request {:path "/test"})
       => (contains {:status 404
                     :body   "Not Found"}))

 (fact "revokes scramble request without params"
       (send-request {:path "/scramble"})
       => (contains {:status 404
                     :body   "Not Found"}))

 (fact "revokes scramble request with one param"
       (send-request {:path "/scramble"})
       => (contains {:status 404
                     :body   "Not Found"}))

 (fact "accept scramble request with two params"
       (send-request {:path "/scramble/test/test"})
       => (contains {:status 200}))

 (fact "revokes POST scramble request"
       (send-request {:method :post
                      :path "/scramble/test/test"})
       => (contains {:status 404
                     :body   "Not Found"}))

 (fact "returns true, if a portion of first param can be rearranged to match second param"
       (send-request {:path "/scramble/test/etts"})
       => (contains {:status 200
                     :body "true"}))

 (fact "returns false, if a portion of first param can't be rearranged to match second param"
       (send-request {:path "/scramble/test/abc"})
       => (contains {:status 200
                     :body "false"}))


 (comment))

