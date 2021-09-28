(ns scramble.events
  (:require
    [ajax.core :as a]
    [clojure.string :as s]
    [re-frame.core :as re-frame]
    [scramble.db :refer [default-db]]))


(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
    default-db))


(re-frame/reg-event-db
  ::scramble
  (fn [db [_ params]]
    (when-not (or (s/blank? (:str1 params))
                  (s/blank? (:str2 params)))
      (-> {:method          :get
           :uri             (str "/scramble/" (:str1 params) "/" (:str2 params))
           :format          (a/text-request-format)
           :response-format (a/json-response-format)
           :handler         #(if (first %)
                               (re-frame/dispatch [:success-response (second %)])
                               (re-frame/dispatch [:bad-response (second %)]))}
          (a/ajax-request)))
    db))


(re-frame/reg-event-db
  ::reset
  (fn [db _]
    (assoc db :result nil)))


(re-frame/reg-event-db
  :success-response
  (fn [db [_ response]]
    (assoc db :result response)))


(re-frame/reg-event-db
  :bad-response
  (fn [db [_ err]]
    (js/alert (str "Something went wrong: " (:status-text err)))
    (assoc db :result nil)))

