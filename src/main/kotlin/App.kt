import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLPreElement
import org.w3c.dom.events.EventListener
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Promise

class App() {
    private val text = document.getElementById("text") as HTMLPreElement
    private val getButton = document.getElementById("get") as HTMLButtonElement
    private val postButton = document.getElementById("post") as HTMLButtonElement

    init {
        getButton.addEventListener("click", EventListener {
            this.loading()
            this.getOnClick()
        })
        postButton.addEventListener("click", EventListener {
            this.loading()
            this.postOnClick()
        })
    }

    private fun loading() {
        text.innerText = "Now Loading..."
    }

    private fun getOnClick() =
            window.fetch("https://httpbin.org/get")
                    .then { it.text() }
                    .then { this.text.innerText = it }
                    .catch { this.text.innerText = it.toString() }

    private fun postOnClick(): Promise<Unit> {
        val headers = Headers()
        headers.append("Content-Type", "application/json")
        val body = """{"a":"b"}"""
        return window.fetch("https://httpbin.org/post", RequestInit(method = "POST", headers = headers, body = body, referrerPolicy = "origin"))
                .then { it.text() }
                .then { this.text.innerText = it }
                .catch { this.text.innerText = it.toString() }
    }
}