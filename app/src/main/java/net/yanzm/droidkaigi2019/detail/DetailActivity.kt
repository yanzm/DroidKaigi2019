package net.yanzm.droidkaigi2019.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import net.yanzm.droidkaigi2019.R
import net.yanzm.droidkaigi2019.domain.Category
import net.yanzm.droidkaigi2019.domain.Language
import net.yanzm.droidkaigi2019.domain.Room
import net.yanzm.droidkaigi2019.domain.Session
import net.yanzm.droidkaigi2019.domain.SessionFormat
import net.yanzm.droidkaigi2019.domain.SessionId
import net.yanzm.droidkaigi2019.domain.Speaker
import net.yanzm.droidkaigi2019.domain.TimeAndDate
import net.yanzm.droidkaigi2019.text

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        titleView.text = session.title
        abstractView.text = session.abstract
        speakerView.text = session.speaker.toString()
        sessionFormatView.setText(session.sessionFormat.text)
        languageView.setText(session.language.text)
        categoryView.setText(session.category.text)
        simultaneousInterpretationTargetView.visibility =
            if (session.simultaneousInterpretationTarget) View.VISIBLE else View.GONE
        roomView.setText(session.room.text)
        timeAndDateView.text = session.timeAndDate.toString()
    }
}

private val session = Session(
    SessionId("70866"),
    "LiveData と Coroutines で実装する DDD の戦術的設計",
    "DroidKaigi 2017 と 2018 でドメイン駆動設計（Domain Driven Design : DDD）に関する講演を行いました。本セッションはその続きです。\r\n2017ではドメイン駆動設計とは何か、何をするのか、を解説し、戦略的設計について話しました。2018では gradle の module としてドメインの置き場を分けることでドメインを隔離できること、IDや数値や種類を値オブジェクトとして見つけ、Kotlin の data class や enum class として実装することを話しました。\r\n\r\n今回は残りの戦術的設計（Entity や Service、Application Serviceなど）について解説し、実装の一例として Android Architecture Components と Kotlin の Coroutines を使った方法を紹介します。\r\n\r\nドメイン駆動設計の実装方法はドメインによって異なるため、既存のアプリ（Google I/Oアプリなど）を元にするか、ドメインを明示したサンプルアプリをあらかじめ用意することを予定しています。\r\n\r\nこれまで同様ドメイン駆動設計の内容については「エリック・エヴァンスのドメイン駆動設計」及び「実践ドメイン駆動設計」に準拠します。\r\n\r\n本セッションはドメイン駆動設計の前提知識がない方にもわかるようお話ししますが、2017の講演内容をまとめたブログ http://y-anz-m.blogspot.jp/2017/03/droidkaigi-2017_9.html および2018の講演内容をまとめたブログ http://y-anz-m.blogspot.com/2018/02/android.html を読んでいただくことをおすすめします。",
    listOf(Speaker()),
    SessionFormat.MIN_50,
    Language.JA,
    Category.OTHER,
    false,
    Room.ROOM_1,
    TimeAndDate()
)
