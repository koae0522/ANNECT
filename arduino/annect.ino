#include <VarSpeedServo.h> 
char data = 0;
//bool check = true;
int led_green = 13;
int led_yellow = 12;
int led_red = 11;
int state = 0;


const int servo0pin = 2;
const int servo1Pin = 3;
const int servo2Pin = 4;

// サーボモータを初期化
VarSpeedServo servo1;
VarSpeedServo servo2;
VarSpeedServo servo0;
//state変数制御

void setup() {
  servo0.attach(servo0pin);
  servo1.attach(servo1Pin);
  servo2.attach(servo2Pin);
  delay(10);
  servo0.write(90,30);
  servo1.write(90,30);
  servo2.write(90,30,true);
  Serial.begin(115200);//シリアル通信開始、転送速度は115200ビット/秒
}

void loop() {
  if (Serial.available() > 0) {//シリアル通信受け取り
    data = Serial.read();//シリアル通信で受け取ったデータを読み込む(半角一文字)

    // if (data == 'h') {//1が送られてきたらLEDをON
    //   digitalWrite(led_green, HIGH);
    // } 
    //     if (data == 'l') {//1が送られてきたらLEDをON
    //   digitalWrite(led_green, LOW);
    // } 

    if(data =='a'){
      state = 1; //neko
    }

    if(data == 'b'){
      state = 2; //uma
    }

    if(data == 't'){
      petDivide(state);
    }

    if(data == 's'){
      petDivideUnder(state);
    }

    // if(check){
    //   digitalWrite(led_yellow, HIGH);
    //   check = false;
    // }else{
    //   digitalWrite(led_yellow, LOW);
    //   check = true;
    // }

    //ここまでがシリアル通信受け取り
  }

}

void petDivide(int state){ //実際動作関数　一往復を基準とする

  int anima_state = random(1,6);
  int time = random(20,51);
  int ear = random(0,2);
  int tail = random(0,2);

  switch(state){

    case 1:
      //neko
      //無効化する場合はここで変数に0を代入する
      animaMove(0,1,anima_state,time);
      break;
    case 2:
      animaMove(0,1,anima_state,time);
      break;
    default:
      break;
  }
}

void petDivideUnder(int state){ //実際動作関数　一往復を基準とする

  int anima_state = random(1,6);
  int time = random(20,51);
  int ear = random(0,2);
  int tail = random(0,2);

  switch(state){

    case 1:
      //neko
      //無効化する場合はここで変数に0を代入する
      animaMove(1,0,anima_state,time);
      break;
    case 2:
      animaMove(1,0,anima_state,time);
      break;
    default:
      break;
  }
}

void animaMove(int ear, int tail, int state, int time){
  //servoのtrue,falseを決める、stateを渡して動かす量を決める
  //動かさないサーボはない前提である
  if(ear == 1){
     servo1.write(110, 30);
     servo2.write(70, 30, true);
     delay(time*10);
     servo1.write(70, 30);
     servo2.write(110, 30, true);
  }

  if(tail == 1){
    servo0.write(80,30,true);
    delay(time*10);
    servo0.write(100,30,true);
  }
}