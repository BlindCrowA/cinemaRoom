package cinema

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val columns = readln().toInt()
    val cinemaSeats = MutableList(rows) { MutableList(columns) { "S" } }
    while (true) {
        println("1. Show the seats \n2. Buy a ticket \n3. Statistics \n0. Exit")
        when (readln().toInt()) {
            0 -> break
            1 -> showSeats(rows, columns, cinemaSeats)
            2 -> buyTicket(rows, columns, cinemaSeats)
            3 -> statistics(rows, columns, cinemaSeats)
        }
    }
}

fun showSeats(rows: Int, columns: Int, cinemaSeats: MutableList<MutableList<String>>) {
    println("Cinema:")
    print(" ")
    for (col in 1..columns) print(" $col")
    for (i in cinemaSeats.indices) print("\n${i + 1} ${cinemaSeats[i].joinToString(" ")}")
    println("\n")
}

fun statistics(rows: Int, columns: Int, cinemaSeats: MutableList<MutableList<String>>) {
    var count10 = 0
    var count8 = 0
    var count101 = 0
    var count81 = 0
    if (rows * columns <= 60) {
        for (row in cinemaSeats) {
            for (col in row) {
                if (col == "B") count10++
                else count101++
            }
        }
    } else {
        val splitter = rows / 2
        val cinemaSeatsFront = cinemaSeats.take(splitter)
        val cinemaSeatsLast = cinemaSeats.takeLast(rows - splitter)
        for (row in cinemaSeatsFront) {
            for (col in row) if (col == "B") count10++ else count101++
        }
        for (row in cinemaSeatsLast) {
            for (col in row) if (col == "B") count8++ else count81++
        }
    }
    val purchased = count10 + count8
    val percent = purchased.toDouble() / (rows * columns)*100
    val percentformat = "%.2f".format(percent)
    val income = count10 * 10 + count8 * 8
    val totalIncome = count101 * 10 + count81 * 8 + income
    println("Number of purchased tickets: $purchased")
    println("Percentage: $percentformat%")
    println("Current income: $$income")
    println("Total income: $$totalIncome")
}

fun buyTicket(rows: Int, columns: Int, cinemaSeats: MutableList<MutableList<String>>) {
    println("Enter a row number:")
    val row = readln().toInt()
    println("Enter a seat number in that row:")
    val seat = readln().toInt()
    if (row > rows || seat > columns) {
        println("Wrong input!")
        buyTicket(rows, columns, cinemaSeats)
    } else if (cinemaSeats[row - 1][seat - 1] == "B") {
        println("That ticket has already been purchased!")
        buyTicket(rows, columns, cinemaSeats)
    } else {
        val cost = if (rows * columns <= 60) 10 else if (row <= rows / 2) 10 else 8
        println("Ticket price: $$cost")
        println()
        cinemaSeats[row - 1][seat - 1] = "B"
    }
}