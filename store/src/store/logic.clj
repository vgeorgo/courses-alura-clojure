(ns store.logic)

(defn total-item [item]
  (* (:quantity item 0) (:unit-price item 0)))

(defn total-order [order]
  (->> (:items order)
       vals
       (map total-item)
       (reduce +)))

(defn total-orders [orders]
  (->> (map total-order orders)
       (reduce +)))

(defn user-total-orders-spent [[user-id order-list]]
  {:user user-id
   :total-spent (total-orders order-list)})

(defn user-total-orders-count [[user-id order-list]]
  {:user user-id
   :orders-count (count order-list)})

(defn summary-orders-count-by-user [orders]
  (->> orders
       (group-by :user)
       (map user-total-orders-count)))

(defn ascending-summary-orders-count-by-user [orders]
  (->> (summary-orders-count-by-user orders)
       (sort-by :orders-count)))

(defn summary-orders-spent-by-user [orders]
  (->> orders
       (group-by :user)
       (map user-total-orders-spent)))

(defn ascending-summary-orders-spent-by-user [orders]
  (->> (summary-orders-spent-by-user orders)
       (sort-by :total-spent)))