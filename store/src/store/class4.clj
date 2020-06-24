(ns store.class4
  (:require [store.db])
  (:require [store.logic]))

(let [summary (store.logic/summary-orders-spent-by-user (store.db/all-orders))]
  (println "Summary: " summary)
  (println "Ordered summary by total-spent ascending: " (sort-by :total-spent summary))
  (println "Ordered summary by total-spent descending: " (reverse (sort-by :total-spent summary))))

(println "Summary ascending: " (store.logic/ascending-summary-orders-spent-by-user (store.db/all-orders)))

(let [summary (store.logic/descending-summary-orders-spent-by-user (store.db/all-orders))]
  (println "Most spent: " (first summary))
  (println "Second most spent: "  (second summary))
  (println "Rest: " (rest summary))
  (println "Class: " (class summary))
  (println "Nth element: " (nth summary 1))                 ; Get first element (1 based)
  (println "Take 2: " (take 2 summary))                     ; Get first 2

  (println ">500 spent: " (filter #(> (:total-spent %) 500) summary))
  (println ">500 spent is empty: " (empty? (filter #(> (:total-spent %) 500) summary)))
  (println "has >500 spent: " (some #(> (:total-spent %) 500) summary))) ; Is there any element with the condition?