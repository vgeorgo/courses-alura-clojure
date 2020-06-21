(ns store.class3
  ; (:require [store.db :as s.db])) -> using alias, but  no need since the name is short
  (:require [store.db]))

; Grouping by :user property
(println "Group by :user: " (group-by :user (store.db/all-orders)))

(defn my-group [element]
  (:user element))

(println "Group by function: "  (group-by my-group (store.db/all-orders)))

(defn user-total-orders-count [[user-id order-list]]
  {:user user-id
   :orders-count (count order-list)})

(->> (store.db/all-orders)
     (group-by :user)
     (map user-total-orders-count)
     (println "Total orders by user: "))

(defn total-item [item]
  (* (:quantity item) (:unit-price item 0)))

(defn total-order [order]
  (->> (:items order)
       vals
       (map total-item)
       (reduce +)))

(defn total-orders [orders]
  (->> (map total-order orders)
       (reduce +)))

(defn user-total-orders [[user-id order-list]]
  {:user user-id
   :total-spent (total-orders order-list)})

(println "Total item :backpack: " (total-item (:backpack (:items store.db/order1))))
(println "Total order: " (total-order store.db/order1))

(->> (store.db/all-orders)
     (group-by :user)
     (map user-total-orders)
     (println "Total spent by users: "))