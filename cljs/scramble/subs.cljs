(ns scramble.subs
  (:require [re-frame.core :as re-frame]))


(re-frame/reg-sub
 ::result
 (fn [db]
   (:result db)))
