(ns store.class4
  (:require [store.db])
  (:require [store.logic]))

(let [summary (store.logic/summary-orders-spent-by-user (store.db/all-orders))]
  (println "Summary: " summary)
  (println "Ordered summary by total-spent ascending: " (sort-by :total-spent summary))
  (println "Ordered summary by total-spent descending: " (reverse (sort-by :total-spent summary))))

(println "Summary ascending: " (store.logic/ascending-summary-orders-spent-by-user (store.db/all-orders)))