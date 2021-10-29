package com.example.nftplay

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import org.web3j.crypto.Credentials
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import org.web3j.protocol.http.HttpService
//import org.web3j.protocol.parity.Parity;
import org.web3j.protocol.Web3j
import org.web3j.protocol.Web3j.build
import org.web3j.protocol.Web3jService
import org.web3j.protocol.infura.InfuraHttpService
import org.web3j.tx.ManagedTransaction
import java.math.BigInteger
import java.net.URI.create

//import org.web3j.protocol.Web3jFactory

class MainActivity : AppCompatActivity() {
    private var isMute = false

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.play).setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    GameActivity::class.java
                )
            )
        }

        val highScoreTxt = findViewById<TextView>(R.id.highScoreTxt)
        val prefs = getSharedPreferences("game", MODE_PRIVATE)
        highScoreTxt.text = "HighScore: " + prefs.getInt("highscore", 0)
        isMute = prefs.getBoolean("isMute", false)
        val volumeCtrl = findViewById<ImageView>(R.id.volumeCtrl)
//        if (isMute) volumeCtrl.setImageResource(R.drawable.ic_volume_off_black_24dp) else volumeCtrl.setImageResource(
//            R.drawable.ic_volume_up_black_24dp
//        )

        volumeCtrl.setOnClickListener {

               val contractAddress="0x2cF238Db807353E87Ee1971D8b2bf59dA5cA520D"
               val url = "https://kovan.infura.io/v3/9bd6056899314a2f94de6f4582b5d8d8"
// web3j infura instance
            val web3j = Web3j.build(InfuraHttpService(url))
            val gasLimit: BigInteger = BigInteger.valueOf(20_000_000_000L)
// gas price
            val gasPrice: BigInteger = BigInteger.valueOf(4300000)
            val credentials = Credentials.create("86ed099e815d398d15321fa7e45af1f0809b4f1a1bb05ee18bd502af19694e5c")

//            val greeter = NFT.load(contractAddress, web3j, credentials, gasLimit, gasPrice)
            val game =Game.load(contractAddress,web3j,credentials,gasLimit,gasPrice)
//            val address=greeter.getDeployedAddress("https://kovan.infura.io/v3/9bd6056899314a2f94de6f4582b5d8d8")
//            val data = greeter.retrive().sendAsync()
//            val res=data.decodeFunctionResponse("")

//         val totalsupply =   game._totalSupply().sendAsync().isCompletedExceptionally
            val reawrd =prefs.getInt("highscore",0)

            val reward: BigInteger = BigInteger.valueOf(reawrd.toLong())
            val data = game.transfer("0xB6a528c43438E054d42D92965A86Da573d5bB02c", reward).sendAsync()

//
//             data.get()

////            Toast.makeText(this,"${address}",Toast.LENGTH_LONG).show()



//            val service: Web3jService = HttpService("http://localhost:8545")
////            val web3j = Web3j.build(service)
//            val gasLimit: BigInteger = BigInteger.valueOf(20_000_000_000L)
//            val gasPrice: BigInteger = BigInteger.valueOf(4300000)
//            val credentials = Credentials.create("0x624451e98974d2ba41339015b7ffcbbe413a090f88451421c43e077adb9b1f05")

//            val contract = NFT.deploy(web3j,credentials,ManagedTransaction.GAS_PRICE,NFT.GAS_LIMIT).send()
//            val contractt= NFT.deploy(web3j, credentials, gasPrice, gasLimit).sendAsync()
//            val contractadress= contract.contractAddress

//            Toast.makeText(this, "${contractadress}", Toast.LENGTH_SHORT).show()
//            val customLayout = layoutInflater.inflate(R.layout.custom_toast_layout, constraintlayout)
            val toast = Toast(this)
            toast.duration = Toast.LENGTH_SHORT
            toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.setText("REWARD TRANSFERED CHECK YOUR METAMASK ")
//            toast.view = customLayout
            toast.show()


        }
    }
}