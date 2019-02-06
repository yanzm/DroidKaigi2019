package net.yanzm.droidkaigi2019.detail

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.yanzm.droidkaigi2019.MainApplication
import net.yanzm.droidkaigi2019.R
import net.yanzm.droidkaigi2019.domain.Category
import net.yanzm.droidkaigi2019.domain.ConferenceDay
import net.yanzm.droidkaigi2019.domain.Language
import net.yanzm.droidkaigi2019.domain.Room
import net.yanzm.droidkaigi2019.domain.Session
import net.yanzm.droidkaigi2019.domain.SessionFormat
import net.yanzm.droidkaigi2019.domain.SessionId
import net.yanzm.droidkaigi2019.domain.SessionRepository
import net.yanzm.droidkaigi2019.domain.Speaker
import net.yanzm.droidkaigi2019.domain.SpeakerId
import net.yanzm.droidkaigi2019.domain.TimeAndDate
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

@RunWith(AndroidJUnit4::class)
class DetailActivityTest {

    @Test
    fun test() {
        val application = ApplicationProvider.getApplicationContext<MainApplication>()
        application.replaceSessionRepository(DummySessionRepository())

        ActivityScenario.launch<DetailActivity>(
            DetailActivity.createIntent(
                application,
                SessionId("70866")
            )
        )

        onView(withId(R.id.titleView)).check(matches(withText("LiveData と Coroutines で実装する DDD の戦術的設計")))
        onView(withId(R.id.speakerView)).check(matches(withText("Yuki Anzai")))
        onView(withId(R.id.sessionFormatView)).check(matches(withText("50分")))
        onView(withId(R.id.languageView)).check(matches(withText("日本語")))
        onView(withId(R.id.categoryView)).check(matches(withText("その他")))
        onView(withId(R.id.simultaneousInterpretationTargetView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.roomView)).check(matches(withText("Room 1")))
        onView(withId(R.id.timeAndDateView)).check(matches(withText("2/7 12:50 - 13:40")))
    }

    class DummySessionRepository : SessionRepository {
        override suspend fun day(day: ConferenceDay): List<Session> = emptyList()

        override suspend fun sessionId(id: SessionId): Session {
            return Session(
                id,
                "LiveData と Coroutines で実装する DDD の戦術的設計",
                "DroidKaigi 2017 と 2018 でドメイン駆動設計（Domain Driven Design : DDD）に関する講演を行いました。本セッションはその続きです。\r\n2017ではドメイン駆動設計とは何か、何をするのか、を解説し、戦略的設計について話しました。2018では gradle の module としてドメインの置き場を分けることでドメインを隔離できること、IDや数値や種類を値オブジェクトとして見つけ、Kotlin の data class や enum class として実装することを話しました。\r\n\r\n今回は残りの戦術的設計（Entity や Service、Application Serviceなど）について解説し、実装の一例として Android Architecture Components と Kotlin の Coroutines を使った方法を紹介します。\r\n\r\nドメイン駆動設計の実装方法はドメインによって異なるため、既存のアプリ（Google I/Oアプリなど）を元にするか、ドメインを明示したサンプルアプリをあらかじめ用意することを予定しています。\r\n\r\nこれまで同様ドメイン駆動設計の内容については「エリック・エヴァンスのドメイン駆動設計」及び「実践ドメイン駆動設計」に準拠します。\r\n\r\n本セッションはドメイン駆動設計の前提知識がない方にもわかるようお話ししますが、2017の講演内容をまとめたブログ http://y-anz-m.blogspot.jp/2017/03/droidkaigi-2017_9.html および2018の講演内容をまとめたブログ http://y-anz-m.blogspot.com/2018/02/android.html を読んでいただくことをおすすめします。",
                listOf(
                    Speaker(
                        SpeakerId("580fb501-aece-4bf4-b755-32fda033b3bd"),
                        "Yuki Anzai",
                        "株式会社ウフィカ",
                        "あんざいゆき\r\n- twitter : https://twitter.com/yanzm\r\n- blog : http://y-anz-m.blogspot.jp/ (Y.A.Mの雑記帳）\r\n- 株式会社ウフィカ\r\n- book : Master of Fragment とか\r\n",
                        "https://sessionize.com/image?f\u003d74af9ffcf2418f075cffcee1402fd9da,400,400,True,False,01-aece-4bf4-b755-32fda033b3bd.b27ac996-bd96-4dcd-8be0-9acd46b4e835.png"
                    )
                ),
                SessionFormat.MIN_50,
                Language.JA,
                Category.OTHER,
                false,
                Room.ROOM_1,
                TimeAndDate(
                    LocalDate.of(2019, 2, 7),
                    LocalTime.of(12, 50),
                    LocalTime.of(13, 40)
                )
            )
        }
    }
}
