package com.example.hw1burgerapp

import android.annotation.SuppressLint
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*

class FoodItem{
    var bun: Int = 0
    var patty: Int = 0
    var extras = arrayOf<Int>(0,0,0)

    fun setExtra(add: Int) : Boolean{
        for(i in 0..2){
            if(extras[i] == 0){
                extras[i] = add
                return true
            }
        }
        return false
    }

    fun unsetExtra(add: Int){
        for(i in 0..2){
            if(extras[i] == add){
                extras[i] = 0
            }
        }
    }
}

class MainActivity : AppCompatActivity() {


    var buns:RadioGroup? = null
    var patties:RadioGroup? = null
    var patties2:RadioGroup? = null
    var burger:FoodItem = FoodItem()
    var number:EditText? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buns = findViewById(R.id.buns)
        patties = findViewById(R.id.patties1)
        patties2 = findViewById(R.id.patties2)
        number = findViewById(R.id.total_burgers)

        buns?.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                if(p1 == R.id.wheat_bun){
                    burger.bun = R.array.wheat
                } else if (p1 == R.id.white_bun){
                    burger.bun = R.array.white
                }
                updateValues()
            }
        })

        number?.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateValues()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateValues()
            }

            override fun afterTextChanged(p0: Editable?) {
                updateValues()
            }

        })
        setChecked1()
        setChecked2()
    }

    fun setChecked1(){
        patties?.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int){
                if(patties2?.getCheckedRadioButtonId() != -1){
                    patties2?.setOnCheckedChangeListener(null)
                    patties2?.clearCheck()
                    setChecked2()
                }
                if(p1 == R.id.beef){
                    burger.patty = R.array.beef
                } else if(p1 == R.id.chicken){
                    burger.patty = R.array.chicken
                } else if(p1 == R.id.salmon){
                    burger.patty = R.array.salmon
                }
                updateValues()
            }
        })
    }

    fun setChecked2(){
        patties2?.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int){
                if(patties?.getCheckedRadioButtonId() != -1){
                    patties?.setOnCheckedChangeListener(null)
                    patties?.clearCheck()
                    setChecked1()
                }
                if(p1 == R.id.turkey){
                    burger.patty = R.array.turkey
                } else if(p1 == R.id.veggie){
                    burger.patty = R.array.veggie
                }
                updateValues()
            }
        })
    }

    //Implemented for android studio docs
    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            var completed: Boolean = true;

            when (view.id) {
                R.id.mushrooms -> {
                    if (!checked) {
                        burger.unsetExtra(R.array.mushrooms)
                    } else {
                        completed = burger.setExtra(R.array.mushrooms)
                    }
                }
                R.id.lettuce -> {
                    if (!checked) {
                        burger.unsetExtra(R.array.lettuce)
                    } else {
                        completed = burger.setExtra(R.array.lettuce)
                    }
                }
                R.id.tomatoes -> {
                    if (!checked) {
                        burger.unsetExtra(R.array.tomato)
                    } else {
                        completed = burger.setExtra(R.array.tomato)
                    }
                }
                R.id.pickles -> {
                    if (!checked) {
                        burger.unsetExtra(R.array.pickles)
                    } else {
                        completed = burger.setExtra(R.array.pickles)
                    }
                }
                R.id.mayo -> {
                    if (!checked) {
                        burger.unsetExtra(R.array.mayo)
                    } else {
                        completed = burger.setExtra(R.array.mayo)
                    }
                }
                R.id.mustard -> {
                    if (!checked) {
                        burger.unsetExtra(R.array.mustard)
                    } else {
                        completed = burger.setExtra(R.array.mustard)
                    }
                }
            }
            if(!completed){
                view.toggle()
                val toast = Toast.makeText(applicationContext, "Can not select more than 3 extra options", Toast.LENGTH_SHORT)
                toast.show()
            }
            updateValues()
        }
    }

    @SuppressLint("ResourceType")
    fun updateValues(){
        var cals : Int = 0
        var cost: Float = 0f
        var find: TypedArray

        if(burger.bun != 0){
            find = resources.obtainTypedArray(burger.bun)
            cals += find.getInt(0,0)
            cost += find.getFloat(1, 0f)
            find.close()
        }

        if(burger.patty != 0){
            find = resources.obtainTypedArray(burger.patty)
            cals += find.getInt(0,0)
            cost += find.getFloat(1, 0f)
            find.close()
        }

        for(i in burger.extras){
            if(i != 0){
                find = resources.obtainTypedArray(i)
                cals += find.getInt(0,0)
                cost += find.getFloat(1, 0f)
                find.close()
            }
        }

        var multiple : Int = 0
        var s: String = findViewById<EditText>(R.id.total_burgers).text.toString()
        if(s == ""){
            multiple = 1;
        }
        else{
            multiple = s.toInt()
        }
        cost *= multiple
        cals *= multiple


        findViewById<TextView>(R.id.total_cost).text = "Total Cost: $" + moneyPretty(cost.toString()) + "0"
        findViewById<TextView>(R.id.total_cals).text = "Total Calories: " + cals
    }

    fun moneyPretty(cash : String) : String{
        var end : Int = 0
        var rtn : String = cash

        for(i in rtn){
            if(i == '.'){
                end += 2
                break
            }
            end++
        }
        if(rtn.length > end){
            return rtn.substring(0, end)
        }
        return rtn;
    }
}