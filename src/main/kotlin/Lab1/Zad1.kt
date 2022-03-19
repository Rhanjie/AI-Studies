package Lab1

fun hanoiTower(number: Int, source: Char, destination: Char, auxiliary: Char) {
    if (number == 1) {
        println("Przenoszę dysk [1]: [$source] -> [$destination]")
        return
    }

    hanoiTower(number - 1, source, auxiliary, destination)

    println("Przenoszę dysk [$number]: [$source] -> [$destination]")

    hanoiTower(number - 1, auxiliary, destination, source)
}

fun main(args: Array<String>) {
    println("Marcin Dyla - Lab1 - Wieza Hanoi\n")

    print("Podaj ilosc dyskow na wiezy startowej: ")
    val text = readLine()
    val number: Int = text!!.toInt()

    hanoiTower(number, 'A', 'C', 'B')
}