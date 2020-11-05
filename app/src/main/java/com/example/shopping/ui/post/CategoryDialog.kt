package com.example.shopping.ui.post

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.shopping.R

class CategoryDialog(val context : Context) {

    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감

    private lateinit var listener : MyDialogOKClickedListener

    fun start(content : String, data: List<String>) {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_category)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(true)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        val listView : ListView = dlg.findViewById(R.id.listView)

        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(context, android.R.layout.simple_list_item_single_choice, data)

        if(content.isNotEmpty()){
            for(i in data.indices){
                if(content == data[i]){
                    listView.setItemChecked(i, true)
                }
            }
        }

        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            listener.onOKClicked(data[position])
            dlg.dismiss()
        }


        dlg.show()
    }


    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object: MyDialogOKClickedListener {
            override fun onOKClicked(content: String) {
                listener(content)
            }
        }
    }


    interface MyDialogOKClickedListener {
        fun onOKClicked(content : String)
    }
}