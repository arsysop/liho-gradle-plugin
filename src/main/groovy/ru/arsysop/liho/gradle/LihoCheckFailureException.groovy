package ru.arsysop.liho.gradle

class LihoCheckFailureException extends Exception {

    LihoCheckFailureException(VindictiveReport report) {
        super(report.summary())
    }
}
