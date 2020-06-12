package com.test

object configure {
    operator fun invoke(configuration: Project.() -> Unit) = Project(configuration)
}

class Project(val configuration: Project.() -> Unit) {
    private val dependencyHandler = DependencyHandler(this)
    private val taskList = ArrayList<Task>()

    init {
        initialization()
    }

    fun apply(name: String) {
        println("apply plugin: $name")
    }

    fun task(name: String = "", action: Task.() -> Unit): Task = Task(this).apply {
        this.name = name
        action()
        taskList.add(this)
    }

    fun dependencies(closure: DependencyHandler.() -> Unit) {
        dependencyHandler.closure()
    }

    private fun initialization() {
        println("---------------- project initialization ----------------")
        // do something
    }

    private fun configuration() {
        println("---------------- project configuration ----------------")
        // do something
        configuration.invoke(this)
        // do something
    }

    private fun execution() {
        println("---------------- project execution ----------------")
        dependencyHandler.configure()
        // do something
        for (task in taskList) {
            println("execute task: ${task.name}")
            task.execute()
        }
        // do something
    }

    private fun run(context: Context) {
        println("running in context: ${context.name}")
        configuration()
        execution()
    }

    infix fun workIn(context: Context) = run(context)
}

class Task(val project: Project) {
    var name: String? = null
    var firstAction: (Task.()->Unit)? = null
    var lastAction: (Task.()->Unit)? = null

    fun doFirst(firstAction: Task.()->Unit) {
        this.firstAction = firstAction
    }

    fun doLast(lastAction: Task.()->Unit) {
        this.lastAction = lastAction
    }

    fun execute() {
        println("start executing...")
        firstAction?.invoke(this)
        println("executing task")
        lastAction?.invoke(this)
    }
}

class DependencyHandler(val project: Project) {
    private val depList = ArrayList<String>()
    infix fun compile(depName: String) {
        println("add compile dependency: $depName")
        depList.add(depName)
    }

    infix fun implementation(depName: String) {
        println("add implementation dependency: $depName")
        depList.add(depName)
    }

    fun configure() {
        for (dependency in depList) {
            println("configure dependency: $dependency")
        }
    }
}

class Context(val name: String)


