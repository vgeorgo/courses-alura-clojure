(ns store.class3
  ; (:require [store.db :as s.db])) -> using alias, but  no need since the name is short
  (:require [store.db])
  (:require [store.logic]))

(defn my-group [element]
  (:user element))

; Grouping by :user property
(println "Group by :user: " (group-by :user (store.db/all-orders)))
(println "Group by function: "  (group-by my-group (store.db/all-orders)))

(println "Total item :backpack: " (store.logic/total-item (:backpack (:items store.db/order1))))
(println "Total order: " (store.logic/total-order store.db/order1))

(println "Total orders by users: " (store.logic/summary-orders-count-by-user (store.db/all-orders)))
(println "Total spent by users: " (store.logic/summary-orders-spent-by-user (store.db/all-orders)))