# Calculator App

## Class Diagram

```plantuml
@startuml


package domain.model {

    calculator.Calculator ..> parser.Expression
    calculator.Calculator ..> expression.Expression

    package calculator {
        interface Screen {
            + onExpressionChanged(expression: String)
            + onResultChanged(result: Result<BigDecimal, Error>)
        }

        interface KeyPad {
            + hit(key: Key)
        }

        class Calculator implements KeyPad {
            - text: String
            - cursorPosition: Int
        }


        enum Key {
            - text: String
            POINT(".")
            ZERO("0")
            ONE("1")
            TWO("2")
            THERR("3")
            FOUR("4")
            FIVE("5")
            SIX("6")
            SEVEN("7")
            EIGHT("8")
            NINE("9")
            PLUS("+")
            MINUS("-")
            TIMES("*")
            DIVIDE("/")
            OPEN_PARENTHESIS("(")
            CLOSE_PARENTHESIS("(")
            DELETE("⌫")
            EQUAL("=")
            ALL_CLEAR("AC")
            LEFT("←")
            RIGHT("→")
        }

        Calculator "1" *-- "1" Screen: screen
        KeyPad ..> Key
    }

    package parser {
        interface Parser<T> {
            + parse(text: String): Result<Pair<T, String>, Error>
        }

        class Sequence<T> implements Parser<T> {
            - constructor: List<T> -> T
        }
        Sequence o-- Parser: parsers

        class PrioritizedChoice<T> implements Parser<T> {
        }
        PrioritizedChoice "1" o-- "1..*" Parser: parsers
        
        class Expression implements Parser<Expression> {
        }
        Expression ..> Sequence
        Expression ..> PrioritizedChoice
        Expression ..> Symbol
        Expression ..> Number
        Expression ..> domain.model.expression.Addition
        Expression ..> domain.model.expression.Subtraction
        Expression ..> domain.model.expression.Multiplication
        Expression ..> domain.model.expression.Division

        class Symbol implements Parser<Expression> {
        }

        class Number implements Parser<Expression> {
        }
        Number ..> domain.model.expression.Number
    }

    package expression {

        interface Expression {
            + calculate(): Result<Fraction, Error>
        }

        class Addition implements Expression {
        }
        Addition "1" o-- "2" Expression: a, b

        class Subtraction implements Expression {
        }
        Subtraction "1" o-- "2" Expression: a, b

        class Multiplication implements Expression {
        }
        Multiplication "1" o-- "2" Expression: a, b

        class Division implements Expression {
        }
        Division "1" o-- "2" Expression: a, b

        class Number implements Expression {
            - value: BigDecimal
        }
    }
}

@enduml
```