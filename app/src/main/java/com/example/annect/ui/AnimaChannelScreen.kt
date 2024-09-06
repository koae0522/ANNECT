package com.example.annect.ui

import android.content.ContentValues
import android.content.res.Configuration
import android.media.VolumeShaper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.annect.R
import com.example.annect.data.DisplayAnima
import com.example.annect.data.accessoryData
import com.example.annect.data.bodyData
import com.example.annect.data.eyeData
import com.example.annect.data.mouthData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimaChannelScreen( name:String,body:Int,eye:Int,mouth:Int,accessory:Int,
                       onHomeButtonClicked: ()->Unit = {}){
    data class Message(
        var name: String = "",
        var text: String = "",
        var body: Int,
        val eye: Int,
        val mouth: Int,
        val accessory: Int
    )

    var text by remember { mutableStateOf("") }
    var name by remember { mutableStateOf(name) }

    var bodyNumber = searchNumber(body, bodyData)
    var eyeNumber = searchNumber(eye, eyeData)
    var mouthNumber = searchNumber(mouth, mouthData)
    var accessoryNumber = searchNumber(accessory, accessoryData)

    //空のリストを作成
    var databaseMessages by remember { mutableStateOf<List<Message>>(emptyList()) }

    //書き込みインスタンス初期化
    var myMessage=Message(
        name = "",
        text="",
        body= bodyNumber,
        eye=eyeNumber,
        mouth=mouthNumber,
        accessory=accessoryNumber
    )

    //読み込みインスタンス
    val query = FirebaseDatabase.getInstance()
        .reference
        .child("messages")
        .limitToLast(50)

    fun readMessages(){
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newMessages = mutableListOf<Message>()
                for (dataSnapshot in snapshot.children) {

                    val name = dataSnapshot.child("name").value as String? ?: ""
                    val text = dataSnapshot.child("text").value as String? ?: ""
                    val body = (dataSnapshot.child("body").value as Long? ?: 1).toInt()
                    val eye = (dataSnapshot.child("eye").value as Long? ?: 1).toInt()
                    val mouth = (dataSnapshot.child("mouth").value as Long? ?: 1).toInt()
                    val accessory = (dataSnapshot.child("accessory").value as Long? ?: 1).toInt()

                    newMessages.add(Message(
                        name = name,
                        text = text,
                        body = body,
                        eye = eye,
                        mouth = mouth,
                        accessory = accessory
                    ))
                }
                databaseMessages = newMessages
            }

            override fun onCancelled (error: DatabaseError){
                Log.w(ContentValues.TAG,"failed to read value",error.toException())
            }
        })
    }

    //最初の読み込み実行
    readMessages()
Box(modifier = Modifier.fillMaxSize().
background(
    Brush.linearGradient(
        colors = listOf(
            Color.White,MaterialTheme.colorScheme.secondaryContainer, )
    )

)
 ){
    Column {

        Text("あにまちゃんねる",
            style = MaterialTheme.typography.titleLarge,
            color= Color(0xFFFFFFFF),
            fontSize = 33.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally).
            padding(top=10.dp).
            background(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primary
            ).
            padding(8.dp),
        )
        Row(modifier = Modifier
            .displayCutoutPadding()
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)) {
            Column {
                //名前入力
                Text("名前を入力")
                TextField(value = name, onValueChange = {name=it},
                    modifier = Modifier.width(275.dp),
                    singleLine = true,
                    maxLines = 1)
                //テキスト入力
                Text("メッセージを入力")
                TextField(value = text, onValueChange = {text=it},
                    maxLines=3,
                    modifier = Modifier.width(275.dp)
                )

                //書き込み
                Button(onClick={
                    if(text.isNotEmpty()){
                        myMessage.text=text
                        myMessage.name=name

                        //messageを取得
                        val sendRef=FirebaseDatabase.getInstance().reference.child("messages")
                        sendRef.push().setValue(myMessage)

                        //テキストボックスの中身を初期化
                        text=""
                    }
                },
                    Modifier.graphicsLayer {
                        if (text.isEmpty()) {
                            this.alpha = 0.2f
                        }
                    }.align(Alignment.End).width(120.dp).height(80.dp).padding(10.dp),
                    shape = MaterialTheme.shapes.medium)
                {
                    Text("送信", fontSize = 20.sp)
                }

                Button(onClick = {onHomeButtonClicked()},
                    modifier=Modifier.padding(top=20.dp),
                    shape = MaterialTheme.shapes.medium
                ){
                    Text("戻る")
                }
            }

            LazyColumn(){
                //リストを逆順にして表示
                databaseMessages=databaseMessages.reversed()
                items(databaseMessages){message ->

                    val bodyResource=bodyData[message.body-1]
                    val eyeResource= eyeData[message.eye-1]
                    val mouthResource = mouthData[message.mouth-1]
                    val accessoryResource = accessoryData[message.accessory-1]

                    Card(modifier = Modifier.fillMaxWidth().padding(top=10.dp, start = 20.dp,end=10.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        )) {
                        Row(){
//                            DisplayAnima(
//                                body = bodyResource,
//                                eye = eyeResource,
//                                mouth = mouthResource,
//                                accessory = accessoryResource,
//                                modifier = Modifier.size(80.dp)
//                            )
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(text=message.name,fontSize = 27.sp)
                                Text(message.text)
                            }
                        }
                    }

                }
            }
        }
    }
}


}
fun searchNumber(parts:Int,partsList:List<Int>): Int {
    var n = 1
    partsList.forEach {
        n++
        if (it == parts) {
            return n-1
        }
    }
    return 1
}


