package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> {
    val driversWithTrips = mutableListOf<Driver>()
    for (trip in this.trips){
        driversWithTrips.add(trip.driver)
    }
    val result = this.allDrivers.filterNot { driversWithTrips.contains(it) }
    val answer = HashSet<Driver>()
    result.forEach { answer.add(it) }
    return answer
    }
/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {
    val answer = HashSet<Passenger>()
    val passengerTrips = mutableMapOf<Passenger, Int>()
    this.allPassengers.forEach { passengerTrips[it] = 0 }
    for (trip in this.trips) {
        for (passenger in trip.passengers) {
            passengerTrips[passenger] = passengerTrips[passenger]!!.plus(1)
        }
    }
    passengerTrips.filter { it.value >= minTrips }.forEach { answer.add(it.key) }
    return answer
}

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {
    val answer = HashSet<Passenger>()
    val passengers = mutableListOf<Passenger>()
    val driversTrips = this.trips.filter { it.driver == driver }
    for (trip in driversTrips){
        trip.passengers.forEach { if (it in passengers ) answer.add(it) else passengers.add(it)}
    }
    return answer
}

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 *
 * - If we consider "smart", a passenger who had a discount for the majority of the trips they made or took part in
 * (including the trips with more than one passenger), find all the "smart" passengers.
 * - A "smart" passenger should have strictly more trips with discount than trips without discount, the equal amounts of
 *  trips with and without discount isn't enough.
 * - Note that the discount can't be 0.0, it's always non-zero if it's recorded.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    val answer = HashSet<Passenger>()
    var discounted: Int
    var notDiscounted: Int

    for (p in allPassengers){
        discounted = this.trips.filter { p in it.passengers && it.discount != null }.count()
        notDiscounted = this.trips.filter { p in it.passengers && it.discount == null }.count()
        if (discounted > notDiscounted) answer.add(p)
    }
    return answer
}

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    val answer: IntRange? = null
    var durationLowerLimit: Int
    val durations = mutableMapOf<Int, Int>()
    var largestDuration = 0
    for (trip in this.trips){
        durationLowerLimit = (trip.duration / 10) * 10
        if (durationLowerLimit in durations.keys){
            durations[durationLowerLimit] = durations[durationLowerLimit]!!.plus(1)
        } else {
            durations[durationLowerLimit] = 1
        }
    }
    var answerLimit = 0
    for((limit, freq) in durations) {
        if (freq > largestDuration){
            largestDuration = freq
            answerLimit = limit
        }
    }
    return if (durations.isNotEmpty())
        answerLimit..answerLimit+9
    else
        answer
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 *
 * - Check whether no more than 20% of the drivers contribute 80% of the income.
 * - The function should return true if the top 20% drivers (meaning the top 20% best performers) represent 80% or more
 *  of all trips total income, or false if not. The drivers that have no trips should be considered as contributing zero
 *  income. If the taxi park contains no trips, the result should be false.
 *
 * - For example, if there're 39 drivers in the taxi park, we need to check that no more than 20% of the most successful
 *  ones, which is seven drivers (39 * 0.2 = 7.8), contribute at least 80% of the total income. Note that eight drivers
 * out of 39 is 20.51% which is more than 20%, so we check the income of seven the most successful drivers.
 * - To find the total income sum up all the trip costs. Note that the discount is already applied while calculating the
 *  cost.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (this.trips.isEmpty()){
        return false
    }

    val totalIncome = this.trips.sumByDouble { it.cost }
    println("-----------------------------------------------")
    println("TotalIncome: $totalIncome")
    println("-----------------------------------------------")

    val totalDrivers = this.allDrivers.size
    println("TotalDrivers: $totalDrivers")

    val paretoRequiredIncome = 0.8 * totalIncome
    println("RequiredIncome: $paretoRequiredIncome")

    val paretoRequiredDrivers = (0.2 * totalDrivers).toInt()
    println("paretoRequiredDrivers: $paretoRequiredDrivers")

    /* find drivers that makeup paretoDrivers
     * - arrange all descending order based on their income
     * - get the first [paretoDrivers count ]
     *
     * calculate their income
     * check that their income is at least paretoIncome
     */
    val earningsPerDriver = mutableMapOf<Driver, Double>()
    for(trip in this.trips){
        if (trip.driver in earningsPerDriver.keys){
            earningsPerDriver[trip.driver] = earningsPerDriver[trip.driver]!!.plus(trip.cost)
        } else {
            earningsPerDriver[trip.driver] = trip.cost
        }
    }

    val allIncomesList = mutableListOf<Double>()

    println("****************************************************")
    for ((driver, earning) in earningsPerDriver) {
        println("Driver: $driver\nIncome: $earning")
        allIncomesList.add(earning)
    }
    println("****************************************************")
    allIncomesList.sortDescending()
    println("AllIncomeList: $allIncomesList")

    val paretoExpectedIncome = allIncomesList.slice(0 until paretoRequiredDrivers).sumByDouble { it }
    println("paretoExpectedIncome: $paretoExpectedIncome")
    return paretoExpectedIncome >= paretoRequiredIncome
}