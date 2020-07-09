(ns hospital.logic)

(defn space-in-queue? [hospital department]
  ; Thread first (previous variable will be added as the first symbol on the next operation)
  (-> hospital
      (get department)
      count
      (< 5)))

(defn person-arrives [hospital department person]
  (if (space-in-queue? hospital department)
    (update hospital department conj person)
    (throw (ex-info "Queue is full" {:person person}))))

(defn person-arrives-with-sleep
  [hospital department person]
  (println "Trying to add person: " person)
  (Thread/sleep (* (rand) 2000))
  (if (space-in-queue? hospital department)
    (do
      ; (Thread/sleep (* (rand) 2000))
      (println "Updating person: " person)
      (update hospital department conj person))
    (throw (ex-info "Queue is full" {:person person}))))

(defn call-person [hospital department]
  (update hospital department pop))