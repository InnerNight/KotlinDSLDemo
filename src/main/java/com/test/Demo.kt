package com.test

class Demo {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Demo().testMain()
        }
    }

    fun testMain() {
        configure {
            apply("java")

            task {
                println("debug task run")
            }

            task("test") {
                println("test task run")
                doFirst {
                    println("test task do first")
                }
                doLast {
                    println("test task do last")
                }
            }

            dependencies {
                compile("com.test.a")
                implementation("com.test.b")
            }
        } workIn Context("mock jvm")

    }


}