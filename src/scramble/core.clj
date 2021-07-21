(ns scramble.core
  (:gen-class))


(defn scramble?
  "returns true if a portion of str1 characters can be rearranged to match str2, otherwise returns false"
  [str1 str2]
  (let [chars-map1 (frequencies (or str1 ""))
        chars-map2 (frequencies (or str2 ""))]
    (every? (fn [[c freq]]
              (<= freq (get chars-map1 c 0)))
            chars-map2)))


#_(scramble? "rekqodlw" "world")
#_(scramble? "cedewaraaossoqqyt" "codewars")
#_(scramble? "katas" "steak")
#_(scramble? "katas" "")
#_(scramble? nil "steak")
#_(scramble? "rekqodlw" nil)


(defn -main [& args]
  (scramble? (first args) (second args)))


