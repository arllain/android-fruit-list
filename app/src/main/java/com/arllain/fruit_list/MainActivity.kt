package com.arllain.fruit_list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arllain.fruit_list.adapter.FruitAdapter
import com.arllain.fruit_list.databinding.ActivityMainBinding
import com.arllain.fruit_list.model.Fruit
import com.arllain.fruit_list.viewholder.FruitViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), FruitViewHolder.OnItemClickListener {

    companion object {
        const val MAIN_ACTIVITY_FRUIT_EXTRA_ID = "fruit"
        const val MAIN_ACTIVITY_ADD_FRUIT_REQUEST_CODE = 0
        const val MAIN_ACTIVITY_DETAILS_REQUEST_CODE  = 1
        const val FRUIT_TO_ADD = "fruit_to_add"
        const val FRUIT_TO_DELETE = "fruit_to_delete"
        const val FRUIT_LIST_SAVED = "fruit_list_saved"
    }

    private var  fruitList = ArrayList<Fruit>();
    private val adapter = FruitAdapter(this)
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.actionbar))

        poupulateFruitList(savedInstanceState)
        setupAddButton()
        setupRecyclerView()
    }


    private fun poupulateFruitList(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            generateDummyList(4)
        }else {
            fruitList = savedInstanceState.getParcelableArrayList<Fruit>(FRUIT_LIST_SAVED) ?: ArrayList()
        }
        adapter.setProductList(fruitList)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(FRUIT_LIST_SAVED, fruitList)
    }

    private fun setupAddButton() {
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val addFruitIntent = Intent(this, AddFruitActivity::class.java)
            startActivityForResult(addFruitIntent, MAIN_ACTIVITY_ADD_FRUIT_REQUEST_CODE)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MAIN_ACTIVITY_ADD_FRUIT_REQUEST_CODE) {
                addFruit(data)
            } else if (requestCode == MAIN_ACTIVITY_DETAILS_REQUEST_CODE ) {
                removeFruit(data)
            }
        }
    }

    private fun addFruit(data: Intent?) {
        data?.extras?.get(FRUIT_TO_ADD)?.let {
            fruitList.add(it as Fruit)
            adapter.notifyItemInserted(fruitList.lastIndex)
        }
    }

    private fun removeFruit(data: Intent?){
        data?.extras?.get(FRUIT_TO_DELETE)?.let {
            val positionToUpdate = fruitList.indexOf(it)
            fruitList.remove(it)
            adapter.notifyItemRemoved(positionToUpdate)
        }
    }

    override fun onItemClick(position: Int) {
        val fruit = fruitList[position]
        val viewFruitIntent = Intent(this@MainActivity, ViewFruitActivity::class.java)
        viewFruitIntent.putExtra(MAIN_ACTIVITY_FRUIT_EXTRA_ID, fruit)
        startActivityForResult(viewFruitIntent, MAIN_ACTIVITY_DETAILS_REQUEST_CODE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val menuItem = menu!!.findItem(R.id.search)

        val search = menuItem.actionView as SearchView

        search.maxWidth = Int.MAX_VALUE

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(filterString: String?): Boolean {
                adapter.filter.filter(filterString)
                return true
            }

            override fun onQueryTextChange(filterString: String?): Boolean {
                adapter.filter.filter(filterString)
                return true
            }

        })
        return true
    }

    private fun generateDummyList(size: Int) {
        for (i in 0 until size){
            val fruitImage = when (i) {
                0 -> "iVBORw0KGgoAAAANSUhEUgAAAEcAAABkCAIAAABb6rrQAAAAAXNSR0IArs4c6QAAAANzQklUCAgI\n" +
                        "2+FP4AAAFy9JREFUeJzdfFusZFd61vf9a+1dVeecvpxut+2227bmEtuBIBiIQiYEIYIyjMTDiEck\n" +
                        "3gIjwQtSXgJKIhQjgSBvEUggoUiICIgQF4FARAoagjQjSDIzeDKOB5vxuG23L93tPvdTVXuv9X88\n" +
                        "7GvVqT7dNp5gz9LRUe37+tZ/v+zNb/2bXyNNEiAAJIEAeIYTkSY5aYIMdMjwcRqSDO4wKgOQnKSE\n" +
                        "2G2YBIeRsuYHjNbhFAG0kD422IRMBgcAFwIAIwG5535+RgaaA3A4SbJDQl+9mTVU/f89SATBiSAH\n" +
                        "AEoAJJgFA0BGKWcJCpCBEDLoUBwgrWDjHzSEDUMAGiYiDYDTJQFoaGUul7GnAEGCkAEOWctvHw+u\n" +
                        "G40GT/O/mbkagpmZAQ4DALOBG4GPG4azYywFBoCIgBAI0CQQgSTATpw+/pBGg46OXGKAjAwRwYQM\n" +
                        "N7ITHnVnf+y4btOQAZAIGCkhOxC7A43F6lQfPoaC9IBBShJgBo9oZI5q9ImkAdgnZzRTbmbuQCQC\n" +
                        "MFDmkwgJQGtsOgn6hLHZ/UdjvgyyHyRUYxbrbFRjldedo0/mYONroBenT5re2zzoPxAw1obsBxHV\n" +
                        "J8w5eujxUaAa65jz9c0flDb6KFCNdcz5+mbj0e8D1I8BB34fFO/HANX3YXx0qD5OFvyj0xbrjLQx\n" +
                        "4bF6yfdtIT4SbQHg7BRtBe2GRNWmBNZHND4sKjpggNof7drnTacB6AA0fxjRSvh+ZKweGtUazzQZ\n" +
                        "qCYLQIcItHFnd44PmcN2j1bp1uSDAaaPBspoPDSqPiNNH2RGAfQmZTU6k4BAdaTrkQcwd+IngN21\n" +
                        "AAD6QOqB5vdPp57Lug+HanwLGWBgBh2sV+ZBgQ4KEBS6+LQ/OvoPDj/WdtI7wN0NN4wHZIoeDtV6\n" +
                        "orMhjoAwzqEOgwJ6OnRRKggFKIzukAFBxXBac1sZhJU79DLZgtykgc5Fpc2bvTD01Gg21RGkBdaf\n" +
                        "rxbJQA2N2KxfFIB1e067auw0UC+HGo7KBv68P7lWDzTUX93V3mVN0IfpCk36mv1ydHgoQJPnPtvB\n" +
                        "wAr12qR3M/JIzASolcAV5YRuddgJ9ioBRqSLm6Celf7+Go7u1fPhGvez3ywfd1VvX7hBCxFm4iSX\n" +
                        "l0++88agHht9o2JFcwzr1T2oBdOvTkfDfm5aId1GJ1oDB7fMkIdVPEvMHnkzy0FCkI5ew9HvqfBs\n" +
                        "ybVwy0Tafu7Jjo2bS3p7HVoWELpndSegyx9h9dBwwopBtzOH+/xTf4t+9mMAzf68cqhl125mCjpJ\n" +
                        "9fxkcfR+zkrV3FnQkPOBh+bmIwYbKySOEDaTbE1Ic2YYAAz648G0GrtwHMnSQIROoRXDvVrlxpa7\n" +
                        "2qz9s16lnDM5ybPdnJfL06M8Pyw+fXX8dNvoNA5GPI9MJYbfzQmb3K5NqBo9M1YSzcr1JmhFWZ2J\n" +
                        "GtlVvRSkIofLOfN4caz53BcL6ISTy1bvF5+61F/m/TS6NGW3jh1fDCVctZpJHMlIr4R0f1QNjKbu\n" +
                        "2E+0WTON5VhnrEC/hAS9NTr7e0mlLw5znKb6CD6TClfh7vbYrL90qNk2s+q1ccNya1Z7ULnsThgp\n" +
                        "8E2Zs7Hy7ZwjNeLUKUCx2xyrxA5Y96Nhi2LyeD5+h5n1/MRoNUM+fh31UkramXZITNJK4aJdUHbT\n" +
                        "6Dm8m9ua5DMPJ4gjVL3uHqxWX3S0YU97tMez7nGPFz2QZk8W5S5Mqk5yedmrPZS7nF5iCKgOeKOk\n" +
                        "OameVoHjZSK6GYwoyVUNwVXMAMao+tr2YPI6xhvvX7HoPCupAzS216eD1+tlCgVZH4VApH2wjkp1\n" +
                        "mntgPyGDd2qjAQnSSIoBUJszR6ctNjxzs2Y/c3avKs5xJUeHRrLRr6tDFi4+z7z0hJwq1JUIXy6q\n" +
                        "eg5PBU1FOxsnvC1DddqoL8Cz6TU4N21+rmb/wLF3zxujEsSKl2B1RRNSAs1VwSawaMVOiFMn/Wp3\n" +
                        "lQyAwwwO+qDuAXSc2Txr7dDZcQZVq0vP8+XH+opkzxskDSPD0kXKEY+F6dUaidVBqhJSDQsWZ1Zu\n" +
                        "aXFShtj5vq3v10adLdW9uU+j/SXRJIb1OT0A1f3xjMuQPZLmR4/TMQ4uGyFOAE1vB1B+d3rhiRBn\n" +
                        "OnxNy0NUx2ZxOT8ZJL6P01oTPF4jgJlsPGkfr+xDoLr/GCNptYGJDCt375a22QBz47nXmSouhXBJ\n" +
                        "6R5hcbJrcRqsTGbQidSFxo0Mq7FOnXc3+Jb9VB5Q730oVGsXt5utJPjKI3tVC4xsAFUp0jl9KhcX\n" +
                        "l9U9TXcVJhkIclrkrO49AzKQjQ+wep+RkR17Fc3inkU1ykPcZ0jiSFgBCXnMq4PaxdhFbjYJmSJS\n" +
                        "tfRyFuK2hVk+uqlqX/VCFmNxsS5PSRIRlJQHXmj4rXXhGxcqtz9a1370xNGID0OurnOm+01nGxe1\n" +
                        "UtR2oKxElo5B3Dktt2odo74IA+HcfhQo5LXSiXlm8os/9ARDJSsPX3qvS1r1j6Pc2uClddgbqevT\n" +
                        "WytKC/eD1HU9cYW+Z13mPmJdsYyN6yn0QTEU4zRqGWKkFaGYxXoJ1SxnJH1+hOBK96r5XKd7O59i\n" +
                        "cZFCltR72G3bZTOLsZez2Vren1AbmmTGvkWnmmjavjpjGcJW7PhkJNx0ElBQ3IqXf1ixkCqGIllE\n" +
                        "dRf1MUm3zHRC1ZEn1dHt8Dt/mXf+MWFDt2UDjCRi02ZFsvMZNsgOTRsi/HWdNuZaOoDLT+YQ7nL5\n" +
                        "Ta/eYI4Iz5VPPCtuZ10otx91xJzS0avvonWdazYdlZCninE6KSYZy7q4zmru9TKnKlBh+Upl2+GV\n" +
                        "X/CTqvRvVY/X8oKrsxrzWLe5gWLyzXmL0Rj5hKGod678bnH41fTG/1me7nMOT8xJZv/VILv0SHz+\n" +
                        "b9jyzrJ6Jk780qe2WVw6ePVtOQHF6dWsWC5u1uVzKc8xuar9b2VclNcBHsKyXu6nfHcSLrtOtHg/\n" +
                        "Ll5K5efGrvN9/cCx16rWsVpHRfZZ1fYCm/jFq29p/qLtf8VfeW+xqFMGs0kiLyr8RH31p1ns4O1/\n" +
                        "GL/29/D5n5mVp4v6hipnWmxfT7LpyZu11weBM49XzAI5QzoK5aPMdZ2XjHGW5xDx6r+dv/PW7KLl\n" +
                        "wgt9NeFzAABfJ0jLeA0frqrfbqyjamOc0WJMjl+o773NlOvFEl5nfmZx/e8QcX3NbvxCBZze0qPP\n" +
                        "vBjCdsYMy4raMz8trz2m8lrO2WSk3IH0Pm0H1RFoULHc+23c+IK//qYC3d0Mud7TLNND19EYVkNU\n" +
                        "64OxRpFIGk9mBdVGU31a/O1Q5IzAy97Enp1bHs4Ew5Dw3s0/cu0z74Stq+nkZqpPQ30Ydz9bOyyG\n" +
                        "EJ+QV6GYwHbz4ggRIRdShcU7xZ1/lmc512QwmZQW+ejrcfvHMIiQGs+999F60p0l1wNcD9CNJhZG\n" +
                        "I2Kri6jmSWtXNa4nZPW9N3T6+86tGJSXt/LpW0VRuILnJep7Ehi2AIRyt4gTWR2uPBdjLLeMDJKT\n" +
                        "CJNds8dWV3ysrke5vd4CjSazoj02oNKwPKNzzmZ8u6MyAPv3/iSnuxa3lY958kacXVK9KEOOxSyG\n" +
                        "7VifaPEumS3PkY7o8+Li88vvvjRfelrkMDUEKUQrH2uspZDXg502XfGgjPRGmTt7SG3P+MADNLUd\n" +
                        "hiurY8z7Qa/RzC7/ZM5JxSzMv4t86hbNFrGYTHauxeKC8uLoG7+S7v7zxdzrE/glJbqFEOp9x/9q\n" +
                        "pGXt/qsP2jzPODrjbF52/eKWxammPR44w9adCqn3Xip3/7hXd+vpE8XsqbreY/kZy77/4i/vXnwx\n" +
                        "2Y9uX/uxvPXDiwtP3/iL/9ph+emb6T/9zCnzVk62FIsFyh9RSGRoHtE9elDO7r5hbgDGtLoftZrs\n" +
                        "T3O7tnsf6vcPfk17djuDxRvv5mq/Onovljc8nYRyN+u4ePevXrWvFndOt279VvXiP/DFyztl5fVc\n" +
                        "EnduTL/069z9QycLVmk2T2blrL1hx4EkidB1dGPFVV8N3ru+W/QgN/vvjTEdS9SgLdp0XJu7ah8Z\n" +
                        "s2Nu6ch5gHBF9UG594Lv3zE3mecKcNa3f68snpg7JpMkFtP6lWuffwEy+vw3f/7Lf/rLBgnmcBN6\n" +
                        "zdQyFEl1NrdX6z25bLzq0ply9TnUW7lQchtvobSIsHfrdRbXzcrt43/Ew1e0aF5CMc/Mpype/Q3f\n" +
                        "+51JPPTlHOm4Lj8Lp9w9bH3x7/8aADJ0q9kSRILa13fUz2U8sZ443SXnxsxr4/wzxcwiSMdbF2a5\n" +
                        "etuWX8sHX/VT2qR9tkMw1rW7iiDmuN0k5q2YqJxZLC1w8sRJF8kb6JLkQ0p0VHkaIG3w2YdlfghI\n" +
                        "558WVGS4L+6FJ380Tp6Jh/9CSYxuwWgIhVm0LKRM+B3mfa8q1afKS6/3DVA6VUax80jQUmoj1HXr\n" +
                        "ckat39denU8ukrTONJlo9+1839r+p+XWJARj2N2q/j2rNwKN0UDJUcOdSC6W9FvfCaEIOkzVnkvO\n" +
                        "LWIBr8GoxT3MvtEDINtU+9qCjimxgQOHw0NpB6NNSOqKgk62Afb6QjAD8IStnQuuxGLC9HV5LYMV\n" +
                        "JUkUEOCZoCVofvyI+xLGWD5FROYTuYGz7EvEK9tP/3kWeaDDUCkf5txP4LxYeOQmnoka6ULTitCY\n" +
                        "DvS5yAGbgvE0TKaYPJpOTi9Mv+3VvWBTi0/ALmUI0UgyUlEA8OTPwutsU59/T8tDVXtiofogMJM1\n" +
                        "Fne3P3WR5qve2eBn9LKwRsANTsdKJDNkc9BG7OMkJuCUwX1E50vXfqOY7nh9Z3rtKVa3lCq3xxNN\n" +
                        "qBgmDKjn7p5jYSnvSsltGup7sokXRZg8puVdKsOdnlFcgCfPGxgPI/G+r3e74ey1st8o1dhD6oAh\n" +
                        "soVESQyMwOHXg16jH5C74qVYbodQwCasRSfA2sXP/IpZbT6HzMLUYB4KIsiiewISIC7fROcxPaQy\n" +
                        "kzY5iC1lfdWDXMmT9VwuACbkrli8e/0rIWwXxSQ++lOo33duKV5mcdF9Kc2olI4JoK4dMmcxufBd\n" +
                        "2E5KrnzqNkGqQ5jQZYFgAUYUN4zrKuEcdT1Y4fsda5a/oZvBTU0aPbD3GBWgIClQoF++9ptGhJgR\n" +
                        "p0ivySJ4CeEqRWQ6j5XnOTc3xH79k2Audk68uMI0V5yE+hQ+T14jFqhPpaU8M05dD2VFxzjPSwb2\n" +
                        "hk/OLGYxo5Gg5lWnTGVJTmW4wBDMjSGUlt4KDumi7ApI8Aghui/TngtwWBknO89/GQrKp3l+FHhI\n" +
                        "RMUtWmmzK4BpchU2s6LwtDivU+8sGTagapP3viJUTZtO36zTF+ka37B70iPX/zOQLGTl2/TT5IHh\n" +
                        "oljBD5XmhqWOT9SYOfH47jOoFkpzxqu1X8gwKXB5CKbgKCZbwSYhztR45cpyNk7ZWQAbWXGtDDcy\n" +
                        "CMxDB2C7s2uvGuSq3cOc5YY4gZ+oflcOMFDOXHnaBxLDQovoWZ60nOO196av/+4/OX77XyJeyvUp\n" +
                        "w6Oia7JNztLxO0r0NHcrGaZpWWmkh3ttfr4ntDFzNuow0hjteBcANykjm7T7+H9RPrE44+J7YTrJ\n" +
                        "mUTMfuTpLiUhnd56N1dItZBscfyFp37kx81s9uS7oMlBq6UprchpjhClpXyhZXYZZrvQ3T7v3aAw\n" +
                        "M/cP2h94v8iZmUpUplwpIVc5VaiW+eC/Q2ahqO69BGZ3V3bqSPl95JOs43z8upZUNjjTIYtPfynM\n" +
                        "LtUspteKrAuW76SUgldMh8pHwVzKjIFxllOV9t5vl5JZ8DHFzkH1oCznGJJ3VJITDhckeNr99J2q\n" +
                        "PjRN4wQkc70APaUj81Nx4Yv30oF5FjzXJ1juvqCTA6W6KEpwqnwkgWHu2MLhi7EoYc8yLxiu5Woh\n" +
                        "2fL2qC2BDg2x1jlO9sOiMnXlh/brCkZPIlgdZl+GYubzNxDrnCtT6TqiDp2F8l71nlDD3fMSB3eL\n" +
                        "gAMZweDVAhazb4m14ZLNv+eT6x5BGIw551QvlnfnwM4wiaHmEs43xw+LymEwURQlBwlFo2vrsf+Y\n" +
                        "EokT6FXXdUvLDEFJOg3BTt+ZW6JAZS6Okbf/SjrcL0rLYVZEmy+3GRZAifLC8v23JlcnLK4jH3vx\n" +
                        "VHX43fmtm8XkJ4QNKeizecgPiQpo2YBwJ42EKCQvciEv+XLSHPmm7KKQqKQ8r2+DC3PJXemU9w7+\n" +
                        "UsCRE1kxW4qT2eLl3yqe+QvBlsDl4pE/htmuqj3MnqgObh+9/vJ0+886nJLuk2P6f0E17jFQU2mz\n" +
                        "jqHrg+9Mr1yw2duL/WOjyqL0tE8EUz59U01dzrOqJfbeYarnqclPFdMkwRP2H7Ht/43JjPUyXvuj\n" +
                        "Wh5r+vhy7729l//H5Ue/CFIuQY0rQ/Z1PoyUXMYmzA8sNBJtrym6EMCENu4oHv921juLkxeT5CIU\n" +
                        "BKiqj2/KPFBIS6VT279b7B9+saoqi6UQSTMzz6k6fvKVb9yqD051dFfHe16H0zdfuv3Nr8wmNzwn\n" +
                        "eRvjiFFGR9NTaG1l9kyEPx4Px4EaVSVaraFf/eVf+sJfK65tvTSZsphGwpfVkY7C4rYVxiQxw+tw\n" +
                        "uF/cffenVBSTyaSyArH0GCwWFgJDcWPrT8zvLFHs4LWv+eRRTB/ZvfJnbHqBDOhiQSIR5l3DXZue\n" +
                        "GZofPjQq4Kzz/p1be7/9i+Xf+rkff/LZ6Pj9+dH+8U3ueC4L5so92TIV6WDy+q3PX7xygbPt2fZW\n" +
                        "KCYhliTNTBJD4wHt5JNS/Jw8Wl1YiMgOAyQMQa4buzbCviQ9tEWu9yp9EG3RD2a5fvqzF371paO/\n" +
                        "+Xff+cWfP7T6iCecLj3OYlqmgMnyxL/57Ss7O394tjObXr5cXtgtZzNjRPstml4YzJtPaxgJowUn\n" +
                        "iMxUoSiJYGZOUBhn0Db3W43gfShUCrL85/76L/2rn3vhsMLt23Fne1Kd1NeLYj6n5nHvXvHq9555\n" +
                        "9qlr21eu7D7+dLhwMUy2LRQkmzSyhaKuFmax8f+bRA/JDJlFMsCMXd0NCvd/2aDvycjd52Ecsg+F\n" +
                        "CjDx5OTwZ7/03H/4n7ev7Pi9o/j1/3YJJ8vnH5s+e+PxclL+qc9fKy9emexc5mwnljv9K7wRFCQg\n" +
                        "FBNZhKdGoxKFlEMwiSJpgdaiCmTuimZnQHXNjitsCX7r3/16T4L7qZSNQ7nau3XzvXdu3b33fkld\n" +
                        "f/TCNIZiUs62t2IxC7NLtBLllIBCCQDexiySSClrqIJ1W80OiwVgDSrSfF1Rd/Ncbz0exphWHwAS\n" +
                        "AIbyytM/tPv0p5Gb9gOhaTAhlRJCY7JDmwKRIZIk+si2m5MJ7m4ErDWGAJrCH85CWi8Qbx5dpWdd\n" +
                        "Qz4s3YjQAGguIZIQUAS4YN3HdxBAAEGuoWTIporngmBDUpHWNFVIZyrxq9LVd8hsmGfsQTSj+cDW\n" +
                        "B6VbO01A7SdbQrNO3u6GITtBB2lOmdi105rcSYTQOqwk/ay70JJoXdedZ4U7JN2PVfWvrtr7wXEO\n" +
                        "w0koqglmGrTW+StEl+kO2GxUR6PXdeeOiM5mUxkMAEzmI0+CsPXXqx5yrLQ/nqmMdfE1YQ+CgtX3\n" +
                        "UB50rrpagyS1X0KDD+u42ko5nu4GDHloNG3+xpCGRrQP0NE7vvuDkDTJLzUZ8vXOhOaTdW2hqLEy\n" +
                        "LbmtnTpWuYSjV9j6txwaL6fxGMc9XTxPcX2ooUbHhq7Lr8m3xm4K7d6mSCpvujbksCaJKbjQfGir\n" +
                        "Wbju7Utg1WFpq6ns37vt66vA6JIPHDKdRdMnZyDLqwl3G5dqpKYQi75LCB0Z1Xbe9H2aNhQThkfZ\n" +
                        "QLTxS2BtQ20Haejt/3Dc2I+1Joxh2NmsWuMjN5V6Q1PwtIZfW75av3m/xwd2Hfkv7Tmtd8Mz+1c7\n" +
                        "6MflsjMPavit8U42drWvyNXaaL8J2QFmK4ggSZdGt7MVyzg2kWfw967aMPvVNkq01bNm6mTzncOh\n" +
                        "daVJj1u7kz5iqPHMAfxfbSvLj1XJBzQAAAAASUVORK5CYII=\n"
                1 -> "iVBORw0KGgoAAAANSUhEUgAAAEIAAABkCAIAAAC9w3GUAAAAAXNSR0IArs4c6QAAAANzQklUCAgI\n" +
                        "2+FP4AAAIABJREFUeJxVvGuwrdlVHTbGXOvbe599Hvfd99XdUj+lFnq0rEeDeBhs2VCWQRgScEGc\n" +
                        "pGJi4hBECKJScoxsUsIuXDZglFgRBBsScOGYskJZWEaOHhgkaCSQRLda3Wr1+97b3fd57jn77Mf3\n" +
                        "rTlHfqy1z213dd06dc7Z+6xvrTXnGHPMMTdn/wYAgiCRAAlIYADGZIAsGAiZERx7LM2SFClPw5dC\n" +
                        "JNuG9SjLoIHb0AqQ0WlnfLhCFaVIabu4Zzve+43JKLkzfI+cBIaMEukosSUtaPJAsmn4SlqkdKqU\n" +
                        "57q0WeAqnm0UXBLbvR8kO0auoIWXnmkKn5mZATADgSCQmJlIIuQud5eUu6kLoQFphxGENCxSyqSE\n" +
                        "gyiFaWS0bIlahSIiFFfBQJc6dhGecjZeSxyilJwyuiPSHBqGGKvsSVeILjzkcy/zZAcpZ3AOdYEN\n" +
                        "05HcbSNt5HybS+PulGJhPCL1tCk5hWWTRJIBCnCY4O4AzIxEymQgYk4pwjscgEi5c0REhEbZNs2m\n" +
                        "ESUixD0wJ+toCsnSbcleNcTE0imTmLahE0rdqr/K4l13gpykHLBjZiPpRrJNwGFLcDsCEYuUGLpM\n" +
                        "zOWDRI/r0I1SSuAg4gXYEWEMLJN1FoSMJJBoBock0AQARkk22oYIS9kYDJiVfpAAhCGGMouYpTwF\n" +
                        "rPTuzKaFCYKHX1JcMVuAN4HS91e70UGGaJPAHLbqsmVsSSVkXZ4CL4KTUToxDD0jTELIBPLA4a69\n" +
                        "ZDtSMrtOk8KiDLQFScXCTGAoom4/TZDBRUkIEQl+ECFDDC4vojwldF0XctCFGI13pHmi5zzqtAhu\n" +
                        "pvGpzJHlEeTgEMNeStloAF0FXNG8DLPAqaWX8cgN3rsTW8Tc43KXV+KBawlmQcK4MzOOpI2URrRJ\n" +
                        "AsGSso0zoUWA3P8IEiEjXECLb0sIBwBLIOkQRDNSIQMAkgxKIRpTR18FYYKMxol8oTRJlqCRYbka\n" +
                        "Ss4M9QDMNjM3V+WysSNNARosnR7KjJgHVvINS8uUJj4QWJCEdRJFIgpZEreDBh9ACUt3J2mJCIJB\n" +
                        "M5oxERJSSjKY0cwCSMBoNJaHA2BngkUGgkSi6CvYONOQNwCFFpZzRh+xiIhgl7IjBsMkpyPSYlUu\n" +
                        "kwaOpEAC8/HwF4XrzONsY+NgHCcD7cDSptkmtKJN6UszE8clNJSFswjjCFEAN41kAqUAIEhBEu5u\n" +
                        "QMqQBCgCpV8CSIBiEFNYqUEFgDkhVgWdwTMTMZK7IwEwW0TskRQTtByGmWFjlI/AYJbMpsk2EPsB\n" +
                        "N1D9SkokoKEfBigTeeVLYiy/Bky9LLOFsNclgEtgP9nYDcAsRwgAhPYvkTuTFKEIiKJAYx5N+n5J\n" +
                        "khHBSBQBhdwseQAwePFS9wDZTC4pbEA6MpRlZ70HE5RyKsNN0tz3ySxP4ioxy+BY0hNJIYiwtKGY\n" +
                        "jZIbtyAHgmL4YMyKpRnE7BoyxsTKzCiBiQAA0BQRkox0yAADzTCsFgaZwXKmJNUIQYICYMqUm2Bm\n" +
                        "IE0EICMDKntdt2UcmTKzlbKwvMMI6yyNjljqjRQGwBJhSaATIlJgJRTISpl5yMyMGxBa1HqnWHQw\n" +
                        "sgdgbUUSABoUJClSUAIlChoGkQQoSV5SMiTWF0miJC+SzEzFmdi7ixlMQTAUw3XHgDRISTYMw0GQ\n" +
                        "KvDhGjyAAACtyMSaKy0JTmSiCzhNlqJ4L/VKGw0SGBQ8etIUNNbUAyC1L0ooitiOBxHIuSILJJHr\n" +
                        "AAJS15EkaWYSgiGCETmb0CsGUkggadi0gKmXbJREk9Hr1qU8JUZAkhwIiIgCILwHwtBBphKUh4pp\n" +
                        "kWkpWwAkSChE0kKREutGAoiQGVOqDyAIJCICCFIAQhDB+mK4jBGSgoTJzMYkyApbtEQDYWLsCxnJ\n" +
                        "UoQkOhQwSSYvc0vGcAbIRBoFVJIkBwAGaGDQRiHR4BEAAiSSEKmbGAmS9UpApAGOutkkQCCBRMDq\n" +
                        "7UvGEIokaRgCHjW6zCwiQitZYkhmpGmQQvJ6fEXFIyCpJvd64CaoLGVwIBwSZTAINFFCL8kMKaWI\n" +
                        "nlBEUACUCDAAlGGenYDCrF4Yg6FFcAKDAAyJhggnWwR1xhpRZF2TRYRDObEIJhQQEQkIgwEW7fTq\n" +
                        "guoXEpgNUcm1FCJBk+SAwcNy/fs17yAiUhpREXCuk0zUo8q0TFqLkgQGHQAURP1CgjxqcqhvSckj\n" +
                        "Aki5SBVkACRIxgRFuBlIFEFRDxtI5lRdVilqb6VIluuOJsAsMVyCFClvRUTNIiQrQIX3JYqkCAIQ\n" +
                        "kdkhAYBFKDzkCHk4mNputfgGJEagrqDFvZGU90NnJkFChIIYhgja4QNnohslSRx1DBGNreTMlI1E\n" +
                        "CYYGyKyl77ZcEkWzet9y3lJl/hIJI8xadpXLoxdShQew/oVAyi15gqj5xwBQZkjBKPWR1Li94B4R\n" +
                        "MCMSAcs1vRKMtgs1bbC4mZmITBWtMx4TBIpU1IShttYEWoAkkpVykABAZsZoKd6MkkKgZcJJGgCa\n" +
                        "SQBRr6wlJEuSIiKlpLpQk+UKbmh0OMGM1hmgum5HhRaglSusXwghSRBDaWygwtvltiCjJnFJSjUM\n" +
                        "6m+r7hdatgqXsVKn+rYpk6gXTxYVTtqvtvsgeP1xKU7SXZWF1G1LrEVTfY1II6kSJMNbVLhHkRwq\n" +
                        "JZCsIEhGAB4VoCrgtLukQ4xSEATMaPVJXCRsTasBOWreN6qdagDZEhRKRkkESEMEE1UBvAJjSu5u\n" +
                        "hgiUIhIqMkPlYxFKNSnX3Yv6I1za/t5p/vq9K8+XV/+tFMNq93eH3Zie+H/vP/h8Sx4ADNZNuFqS\n" +
                        "jHYslXE3/DVSLlCoF3Ydt2ZRTwxAItP/8n1E5SBGsUZCUiUIrEhJ90jJJCBgqT4YItoFawco+AAa\n" +
                        "A5id+ef52VftvHBjo5xcnfnW1aKUyW3HTr7xnHjmyX1t3gt8JSUqATCUUl8ehKHRUwNqAVMPoS6f\n" +
                        "qSVfVrRv7KGiOAQiZUIykIRQQard74rrDcUr8av8y1jP2YwBuMiMCyf/vp/6x9uPffbm/GzYMb9+\n" +
                        "odjmmf7hu+afn9y81D3/KA7K/Kl70vzdl85+oFYBhxCUK9+xBr4AZAxCJlbZpqb9ZG41Sx+Gg3Iy\n" +
                        "EAwJpCjKQpFYH8BJ1rRQw5WKw3R+eAiAMnNxX42mx/Pdk2c/tZxvTTuMNu8Z8rFzV/9o/MwvY/uh\n" +
                        "zeHqzZ13x/b9W/tP7108d/ZsQjAIIwE4VKsFExQKgwkumSA2LocEupBEQYY16QNpFkJILdpgpYQZ\n" +
                        "xCjFITMkQBRKgTyYjAIaa1A4FFBwGBzgwcmfH+3vDgdHlc5yfHR54Lj8p/aVX+eJt5arj8bWvZOr\n" +
                        "f8Arz8623zyc+ea4+MyFrT8HV8tIAashUJFJiECqd0CodE4CrKbBKqm1b7qHIVhvPyQhcocQa2ID\n" +
                        "Y/ACUETOJiGiHhss1dcrAhFw19DHhoftzRbz0cqmB0UOODqjzY69vbz978S1/dHGSeuvabWb5rvD\n" +
                        "wdGdjf8OuUIBUrJ6oyTUKsAMr3wAqaYfRIDBolsYQsIsNX2n1hkIGoio1I1GkyulNYlas3pJClh9\n" +
                        "keDCnNk48tiZlL3J7MJ0dXWjvNSNRxqdHXafWL68F3ky9HtdF5oc37r+icEnXB3QG99uGxSQ0MSN\n" +
                        "NWlIh5xcCtaIrzoBD8PDQNZgFipyQ5V5OyTVeI4id6GW7AYI9QxXg+QKN+8xO/pt3fyF1f4+EKPl\n" +
                        "Y3l+yTHOmvfHv361Qtp/pj/6WsPS83jkS6U0mzmWi2iRSolr3GyBF4QEExysp1GXW/fQdBjfAGAR\n" +
                        "iOKx/kWp3U8jFYwiCAFYLT8MEQhHSBHIGSECGHpM8rm0Kt3qyiy9Oo9ymuxYf3WxHHF5xY6/QdMt\n" +
                        "eax23q68xWExLNLWRIoC1FXq8KLXQwjeyiIVKkysxIy0CuSH6EnScmq1swfcw6OeG8Vo3CnBLAm0\n" +
                        "XH+AmrJLEWR9r/mB9wWj8ak0v4LBupyGciTFjW40giLzWgmIizLaGQLEUr6fNk/ltJ+moxrNbUfN\n" +
                        "ZBWOjMEEq4SgqmX1fLzyxFC59eSSZGWIWmzlDmYE0WSSIA0hIahowOhDDXC5hwHuASEc0dNwLoYe\n" +
                        "i9186VPSKC9/vziMw3L79ZtXf2d+5pvGiws71z6ah2uY7w3Xnktpw3zhbR0AUCLglS9HzTr1ylV6\n" +
                        "61AN1kpiUg3RYK0pMhMkepEAM1FWcZoJDGNicU9riVAAg6DkAE2KKOh7BJiH6xpGXTeOflA3W04e\n" +
                        "FCcpl/2NV+/zVenqyysd7zbuyXtf6EbnfOdUDFeibCZHsMWDEaqlmLHmfQAJ68o0IoqYgYCD9ZJT\n" +
                        "MqPX2KiU0gyohQ9DgIIeUTfKPdomBN3DB1SGAxnMhh7LWaT+kjln936HulOpf2aMq10aYZgNgYM/\n" +
                        "+ZcbB1fG1z8dMKbtlW17P/elsLguI1JlDLUSJmq41JvNVufUPGYJjUKt8TjASKLBDGSyWu0EQhFy\n" +
                        "U4R7mFkZwli1NAHMHWFInYFU0D2Wi7AqCBnc+u6Rj67SeHR0yzGO+QtcPraar44e2xou/HZiB7Pg\n" +
                        "jN3xwTd8NR/PHqkAjsQIydCqIx5e+jXG2aFOA7hQqZQhdzAw17tmoFWNSDW1BQAzQjEaZ7JWGfCI\n" +
                        "MgTFKHFYkEEsBcMg2lbsvWi+HFuUPhtuGq51eGQ8GU3GPt66Wxc+bstnbfV5zC5JY9s4yd3kznVJ\n" +
                        "sa67qTViGACT1aOvv1Bl+UpIEwAXHA6Ze4DBGg8m5HqINT+juEco1gVkLbMkNPAeFIWlSJFjdTXs\n" +
                        "nOedlFbEkjbrcil7imFp5cby+uNWbgpZCq6ujkZdGZ9z3qYQJZeCtcZhxBrLEWtxjN6EAbwi1uUA\n" +
                        "aS7BYalLZVAURY1gB4BkiaSCoCJqsiJkJUKEBIIKhKyUUKCkmF59Kg3o+pdjtT/MLzC+NFz57PKy\n" +
                        "7cbGwTxuXJ+Vm8RqDke33It+N119yssdL2x/Z73thwUqechebxHYVuaBIhpoBE2MJlgh++BrnSZF\n" +
                        "xFpscQVAyBt3FxEeBtStcxcCFFIioDPf+B164tF+9UA/bOyMLnbdCEuVPW6ci7//4V8/dulJzC79\n" +
                        "4jslN09TG72QylZ/UCaj1ZkVXJbQrnE0Hagmfa5FSpDtzlG1SjOp1jtghYCIBoe13wEGVGMqVRJt\n" +
                        "RIQQWkNmK0EjMHiEbJRzxn2MwvGxcX/Tsmx4ohQw0+/70b/3vvc8vnH7330v8QZAC5TTWj0aXTrY\n" +
                        "uPvgtr882h/DBZmiLToqXWjQ3srUGhhVGbKmkq01GCOAXHXOw0MMb7/XsGetEbCiRMA9Wl6DoqAU\n" +
                        "B0eO80P/mqWObW3vsX/GIb9qyzkfeeTihz75U9/1wz//h3jsTZf+4707zw/5/Eb3jHG85bM4uGn2\n" +
                        "RBmriXHBCFnS+liwPpZ6q9R1VsudWyISqrSmTNoh/a6FHslQHNaHVQeJcKgV330JRjvGoVe/6o/v\n" +
                        "PrqYjTvsav4VHT9l5aR3V1fzfN+Dt//saz7ynT/xrmu93vSa1/7Gt3xufP6bY2bElcDpMttVd+bZ\n" +
                        "zdfec/ARQDSz1Ir7euLuVWWEoRZPATGnFhIkXKBkXDcGai1a38I9KAxDDIPcI0JRvJSWBQmrCpWC\n" +
                        "Q5EEt7R184+VTq5sY9i43fqXy7BIR+7qfuwztnH6R95/cnLm/jPTree+9tV05x0+u7xYHcnDxS4f\n" +
                        "BDY1HD2Rb6+XI7yGdpMSJCXDocTc7sutopcR6zJLtPBW+lXIJpGShZASzBBDBRCasRSR5h61bCxF\n" +
                        "4UjJxm//AV5/POPUZP9rR7aPF0189Mbh6tOL3/8Hn/jdLz5y9fqFp798+u57fuDHfunh/7Cf+kuW\n" +
                        "zsCXKUW+8nQ4R/Nr0WgEa+tuvbm8Vac2SKnrhCoTIdRQBplWGXyV4kNgk02llJiSrYqvW1IoQ4hQ\n" +
                        "qBQACEdfdK5//GDvjQVuaXpw8+lR/6jSnasj3749/ejbv/83f2ZxbfNV931m+U3/8hf+xmqre+j+\n" +
                        "VdpaldV1jzeU6QlOtibXfst2DFCss8hhIVHLplATmtbn0JKUoqZetR7DmtKE2LTNeoLDUqviXOvX\n" +
                        "rdlAegFhHohgNm1c+7w2vtEwWH+lzC/4YuaLPn3dO6Mc/6Hv+W8+/MflPL78I/d9Jo+2/22/tfdy\n" +
                        "KTxThuDy5a7fDc/mq3mk2iit664KY4Um4FZfkqwhYjAc1hoBoqbUV2o5TK2/EYE0qhdPESpF7UhE\n" +
                        "JpTiXuCuI2/9S92B2Y3nogyxeWIy2U7H3oqFHV1+8Ad/4uYLPX7ur71056s+W/TcN37DQ/Px9olv\n" +
                        "eTO0Gk3vKJMzYB7PL+jgjqc3/7r7reAk2SIEhKw+DGllQI0GCgqaGRgGEcy1XHH3diahoJIzHCmh\n" +
                        "1sckmBBFCez78GAE+l7h2OgPfP8tKx/n8nx2Fp9hNJmffvPB8uBN53/nqRvd+3/52W+41z/y5Bee\n" +
                        "ubn3Xe/4poPZc6kcWe0+kUdni9ls8tqtuHJykmzeuGATqdYa7hoNCKIbtd6C4rAH1qLHVGop1ZSO\n" +
                        "CqJF0Y1MahIYUBtidNzSJM0wGuXxwZXh1Hd3m+et37MYynBZs0U58Tq767/9oV/8F2+Z9q/5zvfd\n" +
                        "8XVvu7xY/PV3futPfc/F1fTrUtzk+C6UPKSjduVrgx0Zr7aBVotLQjCg9WJkCRXHtP7vsHx1qOKk\n" +
                        "yZr1xQQzuNfIZiAkOJqEAzaRSKK7hgIFrm0Aqw1efSYtX+TkWOrm3ZEHhtW17f3P7H/su7vllfl9\n" +
                        "3/Tbv/GBn/y/H9sb+mkq73rfU8f933D54nDzpW6M7enRFDPElDevoPWuUOvnesnraRz2iA6/oCmI\n" +
                        "iEhgI4oppQitKWSlI2SILflWswG8KCIq0BIpImR24oHvTqv5kHYW89mo/KH2vhrLl/JWN+k+86df\n" +
                        "0vf/+D/9H9/wid1Ir77rtScn+Y6bHz+6vf0j77eydxMYz6+/eHDjGW2eR+m7tKokzUB3AXbY7G0Z\n" +
                        "di3m1uMKoLZIaTKzIKxK5bY+kIQK+GTARJVIiRYZaLwtAsPgQ89+HturJ8ts2176g83EGd7CVJbL\n" +
                        "EY/fP2z+lW/+8f/zp/9i+ulPvv6r/89P/IXXv7rrut/82qnpsPzB7/t2joDx+bBjU+xGf9PSdhrm\n" +
                        "ImAUQaJCU93WuolVLz7MwrUIqdQpIkxV4Ikm4koqrb8PGYcQLIVrCK/kue/DCxDwIpmN0g7j6HI4\n" +
                        "Mmycmcbu4mZG//L1r33yZrz64V/9ex/43Mly48W7vv8X/78vfq2bnHzwz73j/T84ztf/nYBu80Sa\n" +
                        "Pzn4JG2e7MfnxhqbGKE1G4Ja54m1Q4c1I9RacziM/4o2ZmpVvEKAZdZGveAyoZQCgSGHKnwzsWKf\n" +
                        "ilSOAsen061+cQPj7U4WN64fe9P3bm7xG989SS8+8uJ8cWZ767Hnvvbs5eeuPvp7P/eH9937/f9w\n" +
                        "OAC5yMdeQ11bYJr3nmYAUm3xNHXylnYjSREopSL8WoqD3arXAyZDwrrv2gwjSKiuN5AMMeqldERR\n" +
                        "ZcteWAZ1HmWuAzvR7X7V4jJOnrTk+xevpCv/aPv29/yrD//o5mTr2v7Bfcd23nDPfV9eTPnS4//b\n" +
                        "L318+/VJaVVigei6C79fhuT7V0vBoZi5zrNshfkr6DpFiVH7UF7Dpuo+gSEUkGjRfqC+hAIKrJUR\n" +
                        "hcNrGilY9TAzBRX7y8FG176EMw85z8aNr07Oce797//2+PqnfmJz+/huP9x3/sywulmuPf8rP3Bx\n" +
                        "lrefuL4/O/LX4uCZYffCcn9P09O2eDnx9JfLPWtSV0P7ML619ke0li9UJdCm6rakzMyczaw5Rg5z\n" +
                        "QhOxgxRSgjtMyV0SrD5YYoojO0dODVv32N4XC7fs6JtjqU390dv/83PpPJ57+P3vOnvj4pWX/uL9\n" +
                        "Jyayl16wn/2vXjN69gvbo99KozOTU29I26c39r8GWd9f5qlvIYHAWlQ6ZLIQJYICQkwEUbe3aU4V\n" +
                        "1uDwotqipw4ZRyuVAJSiMtSWUsU+eqDvYx4RFx7u88l048nlxj1l//lycKm/ZsMcSBd++J/e+6sf\n" +
                        "xW8+t8Vu898/XW7Yxkur6c/89tXVCLtXHoiD3f1nnujIfvO+QVMNZ6c80faPtScMqT0V1vUpUAP4\n" +
                        "ldRdh+KnIpSq3atapZrtkPCoLKtV96FwUzAChB05D7MHfTHzvJWHGa59IYZ0MI+jx7mx9cAvvO/b\n" +
                        "/tbffCtgWX59sdidzz705dv/9rse/N9/6Z9tn78LYxufPrmaHM/947Fx3EdHchUxpPBqaqGB9RCY\n" +
                        "au9B9UbJa0KzdeEukpYyGKimoOjbc5uahTUlq7oBox2ie8DRjWx7g2nzdD+/kW67m4ndsa/zG5eW\n" +
                        "lzGsNDz1Z3z2l4flV954YnTvREPp/+YPvef2zckHPvyvL/7ee772yY8tr1xaXL/kqwNgY7ksFtah\n" +
                        "tYJbSynkNXhrJzZEISVTy05U68q2tGaHxqnKusLXDJcgOUQowGAQEVqtvLpmlotCcTmbs8TypReG\n" +
                        "+XJ+/ctS+IC0xXRylI6e/vFP/sATe/EnN5aA/bNf+bWf+q4vvnDgr77r3vvecjp0YnzkNWNptSqJ\n" +
                        "fRi6iNpysARFvdgVsOW1/ceGhm3H41ZOq0WvMXP95I1yDUOYmrU4DEWRkFWUElJKlUjbuW8YdQvd\n" +
                        "+aAke/HTfoOWcfRcnhw/98ylhx5/5OILTzySondwnHKn/U8/9q7u2Nly9bHltZew2pu/fGk5dDr7\n" +
                        "kG1sgs1exBbRiip9BLjOS3L4WjOQZLBDelIZbjs9qTlsqjul5W4HBjQcTEnCalUQUIhOZEtP/k5Z\n" +
                        "LVc3UBZKm3AVzl784Ic+84nn3rHafaKMt08ePfWuv/FP3vvd79h//o8vXX75oZ87ElsPlJuRT9we\n" +
                        "CL/4MEZbOpgZCgxM8OapqNkUTTuod8Nav17BkCBbE16YDjtoVDNMxC1uywwZkDJTisFJg1CKPJCW\n" +
                        "V7HaWurozvQg3XYMCwvHaGKrs//9333f6370e1+8+857J+ONe9/wFz716z/2g9/y7+L86+46vr1J\n" +
                        "juMry3nh1Wc2l5/ZOnY29QfGec8+ghFIiXFLb6t4J0HNr02iGZpVn0cCkQwuhhKoaP7PBAJiVK0X\n" +
                        "EoahwMPMfNWazyklLb662B1GpV+V7enp+8a3n1lcgkaxM/lcnx+6snrnw49+6ebNa89+4d9ujvMv\n" +
                        "fPlffOhjD6tf7EW/NyNkrjK7dlqzm5PyAscjz5MWomLELTA/VMlcaFJnRL17TUkT3b1Z7esJyFt1\n" +
                        "UkG0MmQGzJI7SPbemsg+xPOfjyhXfb6bEvubz09etTG96yjGuDJ78G9/8NMdHnvV2bOD9y/P5p4m\n" +
                        "H/m1H37Pe/7Xy4v+/G3np5ubwyqwszM+PjkoZbAddONFGaqwKW8pqN4rVt9dBNc+j6YNvKJ/uVYy\n" +
                        "a55uVJIN80F3VIOlF3fIi0aJqTWt5Ui5fNGWN+clRt2VODg4uLibR5gevf9f//yP3dZ/5oFUzm9u\n" +
                        "3XX82N7+7OWZ/o8P/ey7Hrjrbd/6X6frs+nRnKanylA2tk+NxxmT8qWv/CsRFNHqpRbosdZLsPYU\n" +
                        "tGdYl4pShZF1RvJqPQ5JVZpXasDHbNZZ7ajckiUTOHv6Zr/35FZc6EfvwKk3j09uiA/aUz9/7dF/\n" +
                        "/yu/ysnrv3775PknrlyehU5vHfF+bne+ae9jPzN/EqMTp3lwgWlj2fuy24Hm33nbZVSpLahqr1Bj\n" +
                        "r/+J5NPA3DzWX5MkzMwYWqu/h93oKpRABTJCLJIQzVcj5JxWq7K6wLK/6Gebq2EYrl7j9r3Dgjzx\n" +
                        "5y9f4z98avO5R//k1Pm7NlK3kfPmqTtPnDj1u7/7kfumy9UuUt5QdyyNdzjdTqPF6rbXdq9wVQFN\n" +
                        "YI6A2SsMKoBgCIqw5vqoyYoW9QZ2SUVrM0BNazBW93O1paM+AC26jpK6EQa30QY1nlq/TMduH3QE\n" +
                        "i8u7n/qNN73m+U/9g7+6mt3Y/8qnzpy43S1fuPTC209N3/WOb/jJn75/cvTYCsvR9vbi+P2xdbxL\n" +
                        "y4NcbQ/rCxM8lN5aGz7qihnRpDWqmgJUtQErQwRVhmAmHF4gIYYoAfdgSMWrnuAeZs0BlzLN8PLr\n" +
                        "okxlU61wfH71RrKdvLWhXfZ494npzsZ086s3l8eP7bAURf/FG+N48fGrH3t8/6bF1QvDvGTfs62d\n" +
                        "kl7+g8/+YzkhaE2LqAYD69GrQ0PcWvlfh0mEzGASqp5VBpWoTQ3D2i8XAbNUovk1Gmck3D0n2pT7\n" +
                        "V8GyZGJePlzOvXV+4i8tV7LPfeDM8pd/6YP/5HVnb//yU19Z+vDOd74zzZ79O//DQ+VlTc/u5PNv\n" +
                        "7ndu70/cY5PwU7e/624cFhlcJ1YAqJbutRpdSkCNd9vadFp31loPl8rGQ5tONQFZHd6KqAaNnFO9\n" +
                        "dSnlbkRAcO3fwZJn3W3nOHnjMLvRzV7o0pHZV/PuH+sIrv5fP/nAXedfO6H+8OMfvbIcjnz+oz7a\n" +
                        "4Na5/o4HNexNNjCePnWRs3rJm36zbl6EC00xhLu7yxJRmXj7F2TluUjvfXfrSaMKnkbBAqKRte4w\n" +
                        "iyKz1C89SIBRfL6oXiSuDOdP3ZhMbTF+fULy0Tb2XoiNyfLyPL740dXw5H/xn33bt77j2//j5574\n" +
                        "/P+0u7iw5el2u/0Nw4nXWhxsTa/OXvXAiasfXF+X2rVvcVnrN6sdNbFi8lpurhm5sV2S6X/+HsIr\n" +
                        "D1NE7U02IageHIQ62xQuih4QQGOUViVfnnfn3tjZwQyLxUE6s7Vz3Pt9bpxOO3eOx6Ojo4eP3/Vf\n" +
                        "ft87Hxo+84nCE8vNVw+OaX8pbU67I9f/9OBzZ/USDXKZ1XdGsmZdIyiI1uiJoZo4DxdPxHq86r3v\n" +
                        "ZmuMrMsUCUxA1FQLhURE6/awDJIQBeHwgr5HznZluXvyvlOjdMb92LL3lLOHaXoqsxuuTfoA17WP\n" +
                        "AAAI5UlEQVQ//TQf+b3otkVx+z578Duk+cbxK7t3vOaOvd8yIw2WqDVONxoh0IiA0Oi3pWr51S0k\n" +
                        "YZPbzb16pRqJdFDGChcxRAm5qzprvBJjrt1aGd0E06kBsbqiZ248cnBmlY5hvDXSyTvzA98x5K29\n" +
                        "/shqdF8+/7bl7kWls8h3r7ZPT6/92Xhnb/7qeyYX/xHWzkeJWjd+K5dqm81biq2a5XdtXaiZyqEA\n" +
                        "L/8qyea5rrURmjKHtXoLykqJwVsKH/r6hQ0llgdIOQ09HXhprG9+/Vti8ZeXvl1my3RwgH7Z7b+A\n" +
                        "6YnJzqmDS0/7ZJt5OHKG5ZzK7q91RhKkaLU3bWYIRBX2ybVjKt1C8XZWUf1sh+o6zR2lBMmhbrw1\n" +
                        "ebSmjJySe1Uj0ZmllMxslIwGUl1KeVStEJ7Fkwd+kczd06NuOT5zChs5mQ/d9iK6G9zItj/eTkeP\n" +
                        "75YTe3905ZMW8KESo6YxR0QpgcDhLI+qka4ERB/aXVLUjhMOJShJ6b3vBkkPGc2gKqSarBl7QjBY\n" +
                        "ShEIoQwBoC8yQoIDVkk/5RFGlEsX/8Oz1+6/+3hXkKbH0st/ttq8J+VVHslvf93Gxmx59thzG/l1\n" +
                        "s0+3FmvNms3uD2PLRTQedsolhJS6+mQEG9AdoguqwKOog4sM0HsxWCIMSDCZqaAUd5eZjSeJ1Dib\n" +
                        "Sq1mBQJJJMYT5jG7rnvb5oufvfiR1fHFiI/Hmddm69MkjU+MNsYvxhldR77z0q9IWLsMRSEES7lW\n" +
                        "eiICqj+tQKFqYKglOVvnnkRKh65J1FknhqNEJJKJLplZNcgyogpxo5Qjou+dpIzWgQaSeUQz5Fyl\n" +
                        "SxUNKfHua3sf/60PXj2yqe2v5u39vO04Np+fGr0w2jl2+ecqU7K0LirqoFEzxkeTdryplYeiQb11\n" +
                        "tZlWafp6BAMAeeHDh2aNSsVqYhbJYVBKKAVGE8ILAIajtpIj6nWyYXAold5jvcc5p5zh4X+yy7e9\n" +
                        "7s+PpqevjY9uvPTPT2igIWcyrS2EMjIsrzs0EBMBuSMRlivmMSBrRmJTRHHk3OYYWgF+6cPE2vuf\n" +
                        "kkWEiUMopUYzc05DL49G0aOPNtSj5B5DUbbxfLlSQepSDZ6Uq45dupFVlWw0wqhjvYQpMWeKYQmo\n" +
                        "VNygQB4BQTPAKI86CBkRZjz0d5N0r4UQqxe/PoxpPe5EsqasItXuWU1nw+DVeVlNl8g0MzMLuSQD\n" +
                        "XH1K1o1TlEhWO0CwFCDcg4bxGMyQxISUUPONkbVykEBayoyCte+pxoa5gk2hqgaFZgeJ4Ho4ojVv\n" +
                        "7VChqo/b91JzWxNtEI/hteNcqWXLe6TlnKoFs0tURB7TOlkdtqiJyFoBUwefse5cozW71yKHB2Up\n" +
                        "mcT6gQIpVcnYajmBavJgdR1Xz5VQzaKAGTOJCLpHXWZKMDMTBndUd27SYXOQRLOP1c6HaKBDYqTO\n" +
                        "3J0ikgAh0HUEZcZkIpk7Cs0YZAlRkHOTy+rIHYHiDUkq5zUDBEsWoeq+qXmpcpGqDoYEKNdsYIbO\n" +
                        "0qoUVhgKkSgDcicVWAOSW0UZqujiAkSDQQox08xYPAK5s3r1U7bmQGcVnkCi61JpS24Jx7JVIiWp\n" +
                        "mkXk8CalAajDQYdYTiRV9GRlGfW8aqWac4porpKUUko1EddBEANkQu00m9E9JKXMcZfNjKZwDcXH\n" +
                        "o9Rl0JRoosIdVsVlpdRuplecXncwmt6MSJkVuisZMRpFOeRILQ1WIUEmS4ldl2hS0Kr6Gw4XVytn\n" +
                        "G5NB3ztCgHXp0EjCOt5Cgqaua71/sZIGdR1GGRE+niSDQgVq01Voc0QREcXbStaKE9xr4KK6lOsQ\n" +
                        "XlS7k1SnrD2qxwLrixDh6HuvYG8kvciAUoIgg2VQjT1mAMG2haomGzOmVJ1oJaXq14LWk0WVThf3\n" +
                        "aoDLGZarLwgAc05m1UHKNhenOq2DUsLAYZA8UkqCGSwxCQbQi6hmZq/+X8JSSl7r8liXjF5d0VAw\n" +
                        "mh1DCK9F+SFTZAXKCIVCt3Y6UkLOBrDa03JnImDIOVGRs3WsL4zaQS3Fq7hUE09LaGTOhDGKV/Fy\n" +
                        "LWGCyUo4WlFrXuAew1A6axTWaj/TvdosDl18rSgvBREqxaXwNUTWZFLzY86pJo1Sog6FMgGhLmGU\n" +
                        "qTpBbrKRHQ71kmAdoIiQt7TjDh/aLCKMEbUQl5kZWNs09WbXUcQ6KSapsu9sRrOmoLhHfccyxMbU\n" +
                        "TCgWQJtBDoflZhevDKc5Owi5uo5VDSNbXzvq5GGuf6zNQzYW1NiUaPQAyZxZIkoRU7MllFKZRNQC\n" +
                        "w4wQAnXsrdnma7vDjDki3EGjrXXS0SjlHACGQ4N7vVhUhLzUa9vuqBmNrPZys2bkF5DN1AWiVnA1\n" +
                        "TmzN5Kr5VBV6arHaBthaqhUTD42qrHJl5U4AzQ43pfbOh0G5hq9CRaqkoHqrwmVpzZABSHU8YTSu\n" +
                        "XcwgWXfr0ANcPyqm/nkzc28dxBYGlVmIlhCu+p36qRGHsU7WTwaI8MMX0kM4bGkYSnEzrmdgVUHQ\n" +
                        "Sona7Y6AVAcFUg3TysMq3nm10YilRCltkAfNMQRATLAqy1AAPMordHwc/k/KS5uwrYbxesHMrNop\n" +
                        "6qSCV0ue0RVmRFDCMFRajWhjzutAbnMPOYOon29AYrV0d0TE0DfTA6BsBlFQspSSKSAovH6gB0hG\n" +
                        "Wb+n6meQNCJdhZi1v8PqbcYt2a+NJ3t4/X7tTUKoM9dyuKsUKWi29ju2SAaZqiRiUVT6EgUyRPPw\n" +
                        "IeXk3sxUgBRsWwuW4u2ytzRuNVRSeqXk2gyZ7lVhrvbeZkipLe263FKCSGBSZXDriWECAryU6rfJ\n" +
                        "rSAxkJUsVrG9LqaUMHegDQoahFInv4dmtYIQgqQy1ESumvUlrvtpzmQK0m51eI1UtPGjOj2/PhOa\n" +
                        "oZRaZKBGlyvCo6owTT/3atT8T0T1GtMVtaQqStWPWJLlZFWwMcsRYEKdG2Vt4wMAoyDAfgV31goG\n" +
                        "DTjqDGFrxrVBljqV48D6E3rWHyEQ6zXdsulIGAY15a/etDXnrZHna+9NQKW2kUA2tmJDH+FSgbv/\n" +
                        "/ykAtUcar1IPAAAAAElFTkSuQmCC\n"
                2 -> "iVBORw0KGgoAAAANSUhEUgAAAGQAAABICAIAAACP7sdOAAAAAXNSR0IArs4c6QAAAANzQklUCAgI\n" +
                        "2+FP4AAAIABJREFUeJxtvFuvbElyHvZFRGauVZd9Obfunu6e6Z4bhyREkzLNIW0LsF8EmqQM2O/+\n" +
                        "bf4FMmADhv1iyIBBSgYEy5RMUCTNmeF0z/R0n/veuy5rrcyIzw9ZVeeM4Hoo7F21qiozV1y+iPgi\n" +
                        "5Fu/Mya1kDCziKZmKqRAGK5K0iCiSoEEg9SsIKEQUSEDhAoAVQ2ngCoiCndCiRBTCwYAgYgAAgEB\n" +
                        "CAwAAMITpakmgKAHVBGBpABAUhUiUoMSDlMRUw24UkIIALAk4QFLKhFBJRDKErGY5YgmYkAAEDHS\n" +
                        "AXUwiZIOiIiSTSQBfYmICBGS4lrVE+kAKDltb83MHCpBs0FEGpuZmKiIkFTViDBTEgAaJKlBQiLM\n" +
                        "UiODohBR9v0TTdVADTAQ5iEpgwZAlJWeQJFkkCoBp2iCEJSsbI3J7Oh1SFkpCxtEVUAyQ1QAQgMi\n" +
                        "ACDJnEHSRBuzqqZwVyP75SEQiARshNZwTRYRiWlBkBjVFpgEi6UFiRFZhxpzhpIaIo2xlXGKGTCF\n" +
                        "JEhabaDKAIQoxQAECilZDQBVQiNRHQgyKaiigVAIjPASIImkGqAJQoiiqpDoQubuAFJKEUGymAKQ\n" +
                        "IICiCsBBAEnU3c1UINekAC7YQkj2G9bFMCJExMxABtllgeQo0v84yXiEiPTn/sEEmGr/yCASEQDy\n" +
                        "e+/2j+ew5p5TIjkAALbIpzU402a7bUIACqGEig5SXKqJqilP69OiIhDISTX6KiO0n0L/tx/NWfIz\n" +
                        "6SImkvu6VTPee7wn8HJ+5XQBSTMjGRGqJwEn2a/s32Zml9+9vNhfByCEmCIYoEIABGii7HYj2BcT\n" +
                        "oIjQI0AA2RL09D2XlQAQKIQRkXSwUbUvRdVEAkiD6mkFpEoKiKldVPIiKXK+n5ednBdaEBQRATzi\n" +
                        "pMiqZIho3x8BAnreJwCCydL7RxCnVenlxQiniEkGTpZPVWu44SRWl9v2H9wJVXV3EUFQunT37yek\n" +
                        "CAQMOkNEkppIiggxpQfJLpICpHHIp5uj6STw4iJJxRQmhv4bF9k+GUIFoht5Yb8FpKqEKCL69X2V\n" +
                        "9usr7jf/srH+h8H6u2eLrpe3zg/rX0ImVX1PtENEEvX90+lCR1LktKN+O0tJ7x9olyA9i8XpOYQS\n" +
                        "IuZeAUjKoid9JyWlkgTat3daKE1Uk2kLNzPJYcyhbRALzc6WOUAcQAMTBQloIooWTClIU3Q3pUaQ\n" +
                        "CgOhRU/7cTBBL/ff3fttN9P3hULV3t/YxTz1185vJQeVaEIRyaGuQNDMxFnpxbIjBCJUo5Bo2khC\n" +
                        "YDD1pIQlLPQi1iDqDDUGBrMAARVL9NZ9eyqpEDAzUYQ0VTMGxIKUpFAZMYZAoGZJBeKABAlVTeSg\n" +
                        "afIqBWqWScByCFQqIkPnaElT4knvRIwSxUwI0qEiREoJwVIKPc5neLrDJN29pCEi+r9dNlUV6I4f\n" +
                        "mRpGpSSwEVCRoAAwMUBE7KJxgEesZWgdF4ggn+S3IItIIpGQRCjBEGFTWMChYgA9EnIUHfpNk1Ry\n" +
                        "WEsBYNQEwoWNMebiYJAFKpYbQlUZIarhGCx1rT5thg0hWTPJdRnipGgUsYgmmk9SAe0SZGcTLilF\n" +
                        "c9Eu5hGShIGUAZiqEwKBSlYTEQIaUoVdKgfVqroSOEIlO6OrJEk1CMw9VKCkiBhPFs/MWmsXleqO\n" +
                        "W0QAg0LCGiOJnSQ9aRpLEagrCiylRDKRJuoKKNCWVS4AE07a3hpShgDUrghuZmCT08+4BDULECIC\n" +
                        "tiQKFQQBL5SZLVO6jioIM9IhWcUBOpCz0kNpIcT5HAEY1J0pSVdbVXWP0YykqQAcNGBqTd3C6Nbv\n" +
                        "B5NoJZsaIixrkCEO4mS/cumexhhIKYlWgIxMq2hpUCOFcIaRTDkNVJHmlijqAaqZh3cfrJpVBRBV\n" +
                        "FQ+a5gyy23sRhenYb8jFonczNGqqGgI1sgkhlikBrqgXC02hiekSH014tCtX97Q55l0EVZOZJBHJ\n" +
                        "mxXrtFvu91e2e2Ivb9iG0n9OVSlUqpioGj1CxZI6a198Xwspl39FckTQ2A06GYCRFAkqRYRU6QpJ\n" +
                        "Y34PQBgFSGoOMzGBKFQNILkaxxbt4iO6p4OZnD4MVVFNRIjCYJbU28kGZ5Gq6kCBLRFmpqSaAlAy\n" +
                        "IgiayO0dn7yS7cGMmfdzmnZZmAWxW8JUWjz90Se7Xz3k3X589vgpVnnc8nlrz+1e/ath/uLbXldG\n" +
                        "lYhI3etlM0BMMvN/gEgvbgRAzvmEVMiL2F7c6AleACml1lp/5XJlQkoAVKTDv6APKQeiq+Tp93iK\n" +
                        "/vp3iUBFIRAxADAIRPUkXgEqRRQCHVuVi0kSsbtl+Lvdk8O4arqyfPNobM9f/6+v/6/07Fv/5d3N\n" +
                        "7v7to82Y9wtU7IOP7//t36+ub3R73d68Xj950l4ehkQt9mhcX3P12T/48/nVT79vh9us7DDmBPqS\n" +
                        "u4q4Gc6/21FuP5QLmDCzCFc1VW3tJBlmdgEu/QQ60Dl9s6p2nHj6CskEFB2dSko9jOzeLErKYPeI\n" +
                        "SrrIKVASQdLUgxNAtItkOHIBG6n4u4fVz+WKOc9J6hxBMnZf+19ML+2DJ09tc3y1M8Hd129vN0Mx\n" +
                        "m3f3aXvrz7/Rh/vxgw/8F798dLvyicPVh06ph/12tX6Ubj//gl/+7P4n/9Fmzu4EJERQ1KqInpVA\n" +
                        "RM7P7M4knGr9BBMZl3OB8LQjAMKOqC4iSdJ+/48/6yfab4yZppRFqdq3fMLQIqZqZ+zd75iq6nsX\n" +
                        "oMumntaJrGoq9a/v8a/21/fjpmo+LLms0+FuFXWVhqEeP4X893/5vF0//rne/1ZTIvIwrg1e29XT\n" +
                        "R+X4sFrnzSqtH1+LWcql1Ycch2HcSqvRmnq7su3jXx51kYfHohCFaEfhybpZ6KtKKZ2tsIrKCTYL\n" +
                        "1BSgWr9KRERURCWlBAFA1R6PQ0TtH//TT1RMlCIXIez7T4CYJVURMXnvoapm3ftJN7QXA396hmSF\n" +
                        "zrj/n77cviqrY8j9nRynuH+wwyERRbF9/GHs3iRLf/zZ5//8r/5mVdb/KK9c2npT4NVKWi3Hskrr\n" +
                        "m6vVSlLOpT7Is1WZFguZ3rydD0evjchIJUHX99x+Pd19e6CKqELBc8rkHE4EGarWNatbqItRPp8p\n" +
                        "Rd4B4xPAUT0dd1b7/T/+jqpctmqWRRQQ1TCT/ppIAGpmQAOgKkBHhjz/lkS0DIHooKJwf+H6L16P\n" +
                        "S9nMze7f/vbvfMe/eJGTDKZrhx+rXK+GtpgZlvn/ePNQpng5Lj+YWEpK6+1+qsO3Pkzw4ZNPiyKl\n" +
                        "4/jR7fHLu+PbhzqHiUpjrRVoyjY8epzbFJJuvpruH6coAJIQZiehUL3EW92EQqS7qf6KnWMeBQJg\n" +
                        "Dx76vrrNMjMS/bAUQCljPyPSVaGaSJh1QTuppHuk1KMznKOq0zXZkkEULpTjr+bjv3hhe7ep6cOd\n" +
                        "7eb7v/55GQZ1jkUe/aPfxMPb9vphYdt8+mk9HP744yf/5/OvbnP+TATGdjjKzZP1rZXVdhAfhlnK\n" +
                        "+PDF6+Hmo/bmpSSVZTlIypvBHn881F3zSFcoBzez8svj7pFwZUAQEmfTfglXL3+kZN0edTEy065b\n" +
                        "pwgi3CxpT3SqChQC+8M//V4/P8BFeJbJdBG/C9ZQ1YvoxjlaPkE7wAUSLiKHr47tX95dT3LDtrx9\n" +
                        "k+fg5FdXmzwMoyVQ9e2Dads8Xss8T/u3m0dXeSj/y99/9fXbtt/wQw93mDU57K+vckqt+rL7ajfP\n" +
                        "7ncvdRjL0ytDKTfZvv3B08+/4/d3OpT91y9icyPeyjbZ398/fHYtSbJZUmuMbpQ7IOiu7X0XmVLq\n" +
                        "m3pfE/vuT5gJHRWE/fhPvttVuucDzHI35OekjV70Fr+eGDidlKlCIErQgOV53f9v31y3FHf3nOeb\n" +
                        "R8/S4eF6pas8rFIu2cYhF1BS2X78UXu4r+3osZRR//efvrgO2j0+vpVcsupy/fhKcajg8ZuHw5HL\n" +
                        "PAE96ooj6IcKDPu//0miWzvKD3/Qdq8tuATN0vAP928/34goySRqoj11dXFtfUMRNDP32oF3948i\n" +
                        "7K/3bbZ2sTywP/iTz4HuPsvFZp/x7v9PkugCPU5WACIeWcWAuvDuf/6HGx/56n6MOqjd+nEMi5zz\n" +
                        "eiWrMoxDTpLW6cn3Ppufv75++mh5eLM3l43953/0g7/8ydf3U/zmtzPcb54kxNEslofj7uAzBysp\n" +
                        "oqWhtAUJGMuwmiYdxzIqW9RXd+tBLEzWq+H2ujnw/O3uo1XOWUQInnCY4uQGOy4QAEQKhZ0lSM20\n" +
                        "u4KztqZ3YWYP4szyBQFcAO5FAS/HJO+l4k4KSKrC3b3F6//x737rg8f6dj+2edV4E4EZaLEWw7Rg\n" +
                        "X6+ub3F9ezfhm2++wVYefvmz8eOr/OTm1TLP7k3TzWObNqslt1lbK/7Q+HJq94elXfvwg+/Fs48p\n" +
                        "qTx51NIQY6nXq6v/4vP69Knerq6vpM5teHaTjvsh9Ha07R3H++q+uDsQKk6J9xIQOEX4qhnl4vhI\n" +
                        "71kN99oTXu7e9VTP6MLOchTBBlDUz04XlwTuBQG/L3cBQZDk/d++woyf/z9fb+q8rsTUdG7jOGq4\n" +
                        "wMacV1fr+/2uvrlblVxa2t/X4Ye/fXh1X67Wy/r2Vw/7Y2t+bG2TfBPxaP3K/YuH+qZcLSvz2w+n\n" +
                        "l29uVkO5uiqr9OjDJ4+flJt/8kH6+uXtp6t5s/WhrD9at/lw8/TG377SaB88vX76fz8nlSRCwnHJ\n" +
                        "6IpQAAHslAvo0EpAJrN3KMziYqNPlueClUiqmmkRQfjFrsslEY73kpMAjEhgtBqBgOz+9deP5GZw\n" +
                        "wkRErjbld/6b/0QcKGW+vV5W68Nx8tbk0Vq0HFOsP3u8O/wC66vXDwfPw1ffvN5NZGCO++Gjp8dh\n" +
                        "7R/8Xv7Od5eHh6Vm/5u/nX7+s7YqZZXTXDf11TBye9Crb91YfZtxLFfjMC/lODPmdS4ljSvLT/LV\n" +
                        "5md3F9Upkk4JxbOKdBG7WPoLSDwhJiQRvid0tB//6fcuDkLUQSVP6eJzclYuxktEGEwMCTA8CIgG\n" +
                        "48Wff/Hb43d2//B1RstzmJlVf/3XX4VIg+bjFIfZg0JdnNNh0soXX7x48WbyT25F46sXf78/vN1V\n" +
                        "+B7f/qyk0rB+dphTscF/9uXafbPKm1w2U11nFD+MT8r2dz/RcVAJvb8fb68VoZEw2vT8MG/08Y8e\n" +
                        "L4u2adbDvPt4Lb2qQg2G6Ds3BWGHmAzpJvximkWklztV7aJP+v5ZMN59oEd5lxjyEp2jq5727AtJ\n" +
                        "IpQ/fXj7cl8ADahqgvzGf/fjpqlOMyMQMTCeqd6ybfYH8WVUGVW/88Ezvjj+xb/767zaDgN+9Al0\n" +
                        "wMu3y17XDxN31Xl4UR6vh8dXNm63n393+6PvI9hCBav5H16nD7N+sBp+4+nmpm3G+foK47i+fTxk\n" +
                        "4/1ffQ2fRpOtrre/OvbdBluCkBKUno0TqKpG9I1SxH8NWJxSAH5RLPvxn3z3vVLNu5zUKYf1rk4l\n" +
                        "dCDEFD3bKyKgQnj/V79KX9fPP3929/Pn61Kqtwx9/W++FOcwpryfNsGrZKWFUDNjtNxK2iYdt+v1\n" +
                        "fv4n//QP/9W//5uS4+0kv3iug8rmkbx58dVvfu/TJy1vFl4/fTZIDPv7PN9ltsf/+IclFGUc9g+x\n" +
                        "yuFLjPrT5w8ffnJ7mBc8+wAxjk/KtJ+WOkdr7WF5+/HQgzuSVFGFxylbyVPFu+9UQT2Vjc6mCudo\n" +
                        "HID9+E+/e4mh3herd4acQC/V9Soqz6UqqkcNx5s//8XVMtx/8VadqG61bcSau1Biatri5nrMn23H\n" +
                        "+0WS2GrweR6lbW63sdvbzerhyze/8fiZ3ev1Zx//4udvfm/U33b7+DjefjOtY3yyWt1s5dEnzx79\n" +
                        "1rdWn34yXm+zZdQ327EyrYcBUg+CeHSzBtUkSAyP0+HhQe4Z0PBoh8Px2diyejQxa0LpML7naxWq\n" +
                        "6XQu6qJQ0UtBJE6KFf1gzuWvi3eLEAICIQiqKuERSkolE+LkE0NEQmCcmsxtKFvKw1AK5rnkfHQn\n" +
                        "YB4j4vbJdbs/lJ86NmOopvnwZFM069iW+83W7+6/9ye/+Q9//ovP8jZept/+3R9eQ4YR6fGTcf/G\n" +
                        "2LAdSym2rfLy1Wp8vSoDSh0+feYC3a58uov1tcxzzId0s0WT9cwXX7+6vclN1vx/v5lwXI/58a+m\n" +
                        "r75X1BThhUYJmrInnijnJFeAiRJEY5wDI5Fz/VMiIqlFd4vvUosqjMhmizfWFlAKhTAwIgQGvoNj\n" +
                        "r/79l5h9XLsXTeBybH/w3/7Hf/k//LsEJGEq5biftk9vpJh7ZKmb1WZIYasiq7I1bsu6/sVPP01x\n" +
                        "uC4La/rYbr6eVptV6LJ6moerUa9WGHJjS9+5GZL5bd48LHqdplGS1FhdcbVunlJ80J6/yExtHG8/\n" +
                        "0unFwT7Iwy/1hqt22K+PC0AEe8FZe9FUAJ5q7KoacJFG7yaXF93q5YOub0mQCbr7BZf361p4CvRE\n" +
                        "J0l6hVk4unPoGCJT4xeTCQ612rJky1n03/7zv0ybFd4cNFnO2VZ53I4JXJuthiuTuh1yXK98t8vH\n" +
                        "aXu1KjdDq4drKm6fWD1sfvejNDar1dY36YnpajUvzOuxlSQog0z24bUOeaOVuXgqOqlFuD/ER9f8\n" +
                        "8q2+vVue70JTDWvWJMpmVV69nUgkTZWt7w9InWOgZy+PMNFzwfNsswAQCjpJQE9Fjks56CSTkKAE\n" +
                        "QiJCTSIg0s+oJxLdHYGK8BqrtGrLshkLgyZa4d/+7qMX91MKJ5NaXkK3V+Xx73xU/+oXZmnxlo+L\n" +
                        "LHU95M2VlCu1zUa36+CcP/okJ+XKdN7Z42dsL3STbKJKSrbIkyscwNUIzXKYwb01SjKXpKz24lW7\n" +
                        "uZlGyuEY38y7h6+vP3321b/+2ylitcrXL473zzZQDUhRFWekHBE9GfN+rPK+vSZJhoiRFNL+4L/6\n" +
                        "/BJzRzQRzR0PoClhqtLr8tFD0XNiCAogQY5/9VLmug7/zo+e7l7HsjvI4u3VfrtdjWpmFuDV7ZUZ\n" +
                        "9PVRxPJmRcnCJdtw87hsfvixZbdHm/KDx8OHj9LtKj0q2Ka0SUgLh6wl0ShXWVcFBVpMOi5aFURH\n" +
                        "SYJK08z7vddFD+FvjjPgsx1+9SJ98oG/uT+QVbH7YNMYIhqAkgKRZKQI+H7kK+fHGRTI6TQvEc/Z\n" +
                        "TWoEFkAYGdZ1jWRr3rlXEREeCLi7eCwtxlTEw4bxm5/tTVJZl1JMDWLpox98MKyHESH1OAbF1NZZ\n" +
                        "LKeMvNlsvvMkP7rCckg3K300igjSIgMbDpKMY5F1sVGRTK/WLAIbYFusbjFeiemr569ktUbZYHWr\n" +
                        "200c57TeWo79z563zTYPSQ97tKm9emU/eDKwbeYaERZneRE0YXU/BWtnZey+7oJCSQpAOtk6sApz\n" +
                        "v4CrUZFJqMX5K0iCZ4tHCuQdoiUObRk3V0vj8XjUzLJetcmzWLlaffG3vwg225Zpmo7zVGsVGBF2\n" +
                        "c5VSKTmXR1t79EjLqpS1QC0X0tN6pTrLZouhYLxCugVGcdAGWRbISsLg5fFHn8I2WF1jf2Aks4LM\n" +
                        "2PlqO2ieGpptDKuM3WH+yTfl9ma4Tiek7QGgtiBZAkHBmSRyIRu9y7vAPAIQVYtoKtRF39n1mexn\n" +
                        "fQFcvUzUUYX2YDsCQKWFYJG2mw5Tm7MqHclyXqV5iVc//6aKHl7tjy+OsfC4nyICV2uhYAk1wyoF\n" +
                        "wBqtZKf69SDIpgOQKSXkiFpRBclhG8YKIlg/gk9oDqh4IpV1QS4pGpVL3euYpWidD3Ff66NSfYjc\n" +
                        "IHF88QKHueMZAx2uqj0C6rIm2p3br5UaSVLCJAHsxUN1huGUKgQh3loS+ikJw5Bk5R2Ij1Oa1UQt\n" +
                        "qrvHSsK0AYvE+mb16KNtvlnN0USIY2NzB2OOiNg9TNOvXgYiIkIQN+MsBlVJJdbF79yP4rPWg93d\n" +
                        "SXtLtAIdEKCHiGGesewQE0iYQlxaFQ9Uj2DojFYtg6ZpX/zzp/71q/3u7dGsYsHVKEQEIzwipHpE\n" +
                        "QBgqDppLxMnfXRToHT6XuFQxVFWD0YtIRYCQghRgl6l+TL1ATz+/0rw1OdWPRhxiLmYApmmqTRth\n" +
                        "KaVxpesBq4Js5aNbiBC+hO8eDtM0BTy+fuCxzbvqB/eXLR/Fq7Qd0VYbzblcI8Bp4rFJOzJnlEQl\n" +
                        "jwfYhHpELL7fcT4yRfOJk8V4My8RxVo9tp/9ym8+mYG713uVFCqthXVsCYqaMtwtmhtRGYB2ygOD\n" +
                        "l3xUl66O4N/FhmdTTyEE9LMcCewdL8FPh92jwgsgg6u/PJhHrjLkguDYIMR3fv9b+28eluOMpaZV\n" +
                        "5jT5sYoihZsgGdRplhhNGAq0vl6D+5xoIAKhCrQ6OzMCRkSlCCKkOeZQs/qwPxJSF19qm6vvjtGO\n" +
                        "lZwefJmmV7vZTFZPHh3bPGt6/uG1iIAUaqcrmJqYGCS013vYxQlUspMRLy5R3f0djU1aLATVLrCr\n" +
                        "5wnfxx0d/r4HR5g/vKJJROza1I7z6JzpovziX/60TbOIYD0Mv/XRcVrmpCglxtEFh6kuk89eq0cc\n" +
                        "5lormi+aHc0OtR53cdi3Y/WK6bhkX2I+cDdjX7U1mYPNW53r8eDuuc3h3pbq7qEaNeoOrnH/8KZN\n" +
                        "dfsbP7q7e61iraS+F0CbonOSwoMtAjSHdqp1T7Z0ksjJxp/sV0op4T3uZRG0iPfIotEz9vqeMhN2\n" +
                        "RrSQkFKSj+p3NWumcDdPOujx/riKgAtU1k+u3/ybXzSnv76/8zlWa16VpGvZDHazai/vDk1Wo8pt\n" +
                        "wnI3r4fx7iGlAaExs6kE89SWEgYsZKgXEXdvMVOUQPFgbaQcxadlkbYsNUeLeoDZSt/89d+mm+1S\n" +
                        "l4cxA3IiNzgkCekB0NQAUdFg9OSfIMieOI2LrIFkvCO8UnSOms7wSkVTSielAwl2oXSP1H1wCMAI\n" +
                        "5uvM17Xt6y4ONmLFUEr1GES0ycPXL3POqDEOFq01RtqsI2Iy2N0s7lZKnZtXsYcJoXWqYeI00Vbh\n" +
                        "8MXEq5GhmvKy7C2l1w+43brQQo++eHPl+uphp7m+jjTGMnmtLQ/eljRm97pkvL0eLwzSFp6kCCkG\n" +
                        "qdTESgihUBFpQRUA7Ll6ORORTWh/9GffP4FSiUxDz1KBALyd2OSqhrPVU0hz4pSVICm2WtkXr7DA\n" +
                        "nKmFT4vPPmRrHiqUhvCWU2qtpdW4HKcUYqOl2vTWhKj7KSBoU7QlgirOZVELl6NGinkiW1uaoLZi\n" +
                        "yem1psGi1rbM1R2tOef57k7awqhLs93b414wNfdpQs67usQ2/fJbT089HkFmJWEi7C+dvF7Xt04U\n" +
                        "9YCYJPQuD0CU7pr6EZi7S6rCAriE4F0NkiR7pjECkj3cFGxCoTuTZb3iISnUpdbUOE4RzvjoWX5x\n" +
                        "57XefP7s9U9foC7JuKRU6jIfjjpEXsF/NuWsqYzzfH+NpIOy7mI9Gn2OeXWtiyxKRg3LqaEs0gJO\n" +
                        "eDssIow6sk51sIeHQ1FGYyAtbeE6lhdHLDpsrxZZlqaw4ZSPIUVVCFHBhdlAqXRTiwgzFYSL9sYV\n" +
                        "XhI01Cxu/9mffreIkoQqO72WQVJwLuqcQwQAjFBEBIVggGQjSe6/fmWzF0eb23d+9zuHFw+FONwf\n" +
                        "TVAPi0QQYqCyjUWj+ZPf+1G5Ut7fVSVUWGco9fOPUI/BWo+zrrJoW2qAEeuh1crWZGF11uqc5jqJ\n" +
                        "H/eH4wKtq7UuB7ZWGbVGrU+excNxcV8kaq0xjq+ebaZhaDzTGSFUsc4KFBELAfxUObYIVzlVoS8Y\n" +
                        "NUiK2o//7PsSUZNZa8Ko4fYegQ3obQq9j0mJnnUgT+1K8AgAwwfX/OVOpjDizVd3ZcxtmU1StEhJ\n" +
                        "Nje6HH243YYv2ghFRvjbfXjTJN4qEeNqFdMuaoubGz7ciUSULcG6HOPYmqpIWwyMhUTUFguPCwVS\n" +
                        "VRkNSE1Q1dlkvrujtP1+iUofVnPiz791G3qqfprmimYpETTrLDwLk0tgaKbBd1D+kmUQYQKwIAql\n" +
                        "CVQsaVJGJVDP9HSxxhYRAaWHijhUmgPaY0aAojKZ54RVUCQ1QQSjhGW9ebK+f3mAKrDcfryON0sE\n" +
                        "X3/14mptw9Mxffa4ffHKyrA7HKxKukl4OBSqN2v3C3xCajBwrvF4bHf3kAhbJUTUUCnz7sBl5Noq\n" +
                        "90ubSPcorWIBptAo3Lf57nqrlAiKCYCGmtTIMJXe0Cb6rgdIFaQqug/7teoyKfaHf/Y9QBIQauIk\n" +
                        "KEH0vpEuWX455s6AhARNNeKkgyQJBmC7BQ11qUq1bKKQhdOuTsclvLk3UQlCAJOgiUmUtY0fPFr2\n" +
                        "+zodG6okQ7jIxHH0ealBaBOV1hqQBbs2h6suNc3TcZZV5KHNy3TYNZujxezKkMMuDvuYxyHyeCR/\n" +
                        "8vFTE8DObRSmMOkBG0jrlCoRCgo0uguQdzWL90sT9gd/8rmqwr2jA+251hNGB71bmyBJJJDS+4OC\n" +
                        "vXh4MmeB4Xq1//oVp7jarg/T/OTZ+vD2OGyHqJ3jjaD4VFM2IJKJZrFtTp8+Pv70ZeMSIWoyLUe1\n" +
                        "aM1r5Jj23N62ZeJ6CLN62BG2BFwSdlOdo+5qkzL7QugxDe3hrdOXibtd2x+4300N8ryk43ZVNfRM\n" +
                        "TBRTCDodXc1SkgbyhKMUDJETXHiXvDv1NtH+6J99H0DUipSs8/JPzS6ICFGBnISL7DXu0ABJv0RS\n" +
                        "ERpojLS68ue741Rbjflh9qSaklcPNjGDUDX74mU0UXFDq609fx0Jul7FsmhWhx+razZ3srBNzhTV\n" +
                        "lwZj7Fs0DzmkR76flirOCPi8281x4DJP49V0nOsx3e85eXBYHZRffPgIIqrOk90R6IlTZAA7lVhF\n" +
                        "48QYxJlfdBGoroMR0fUPJGUYEBREExNvEBN16SaJJ/kREQ8HEUKFGnjyk70l1CO2UVfERCloSAK9\n" +
                        "302rbEMqdZooXNBsHI/OKrIh2SDCIHUzyLyft6MfZgTu9201pPZkqy/fmKjUxfJ9SyvmNM/kxniT\n" +
                        "AAAMSklEQVRHmd8cDlMpxT2mh5mxcPEqgpfVk+3Wj5blmyh5GewnvUgMkMinCgMvNqizSwMsalSc\n" +
                        "6ssIKHAuFJ7P60RgOKlhighVuKdOXe7FG0pEdNwGIHhqqeuOFGR0aE82OoP0sG9dLc8P2TE7Jcni\n" +
                        "nnOKpaUiOVu/VAEdk6aQlAIeMJ/mutTWFgw5wpY265B9PkRo80UKZjpRphpRq3ttsx3n43yMZZkm\n" +
                        "xLHpcpBjlVlxeHk/Qw+he1meP72KBFDUKJ2wp5JVJRlOdWZN0j2bUCCI3jh7Sf6dUw8n8HTmm6qZ\n" +
                        "e6Qi795I5x8A5CyQqvQIwaWr4tQlTSHO5esfXk9ZZpU5uH48NERVjJ+s923eNSyJuFG9KgvSJNFW\n" +
                        "w+R1qsts6qMdazu22jwF9i32i+6XnI+1TQvuH+6mw/NjvXuw9bS8merufndcxifHB/H17f1hPtZl\n" +
                        "t2u8GqbmB/G/u91ERA7T/I49TIGbRISJsvO4VVIggF6m75XX97NaeM9+2R/92fd7JsxEk6CR2svc\n" +
                        "CIEu7qbK6B9Tj+ixJp0CRI+mI0Ajw2uNCGQRq9hTBSD3x2pZ28PSWgxrnaqzxtW31sf7oygqyAjb\n" +
                        "JkOdPnrK+ZXmwbXM0wHCcHG2pabW3NHq+ltYWXt+NwcWuTrs/Xh/v2+yiLVpbpaOi98f21LGL2+0\n" +
                        "lqSqSARdRMSM2i2UJoGYqtBUTCwEgUj2rkz/TlV/3dLbf/pf/wCAiFLVvR85L8WhzlYKhoqCDiRE\n" +
                        "APBwiki0YE/Sk6STiACkblfhR1soFXnM0RpM1UCwKdX0sJ9ufvhs9/JBcyCZIGIo2N+lspFxs7vb\n" +
                        "h9TqiLxqjun4sDSvC5bj/vjQavPjEftjO8IejtUVmtYo6X7aVZGD2KtHOG7WIlDLajAzM1Mz2MnG\n" +
                        "J9VTn7XoqTMkqeJsgX6dqstzhVBE7I/+2fcL0flYxjBTj7BzqfaUz4mTGvo5VlLCoAEhQYZH6+FB\n" +
                        "0BnRnLG1qm6LBFSyTREhgEokRMpK2b8+iGLz2QfTwy7d5t3dnuCh1uPDXauVyhacGmubliVcZHf0\n" +
                        "JsN8nOrwZHf/sPdoHnmzbnMNifvDjFXeV315I3er1H9IEKqaUkopiYmoppSKaYDDkNib9gRi1s3M\n" +
                        "xf3xTJp93yGKSBKRRTxD67JozhFhgqCbZEgEIhq7g+ifj2hCMCmWUMA7mRWgRrQQTabMUmewPhrv\n" +
                        "h1a+nPMCMzlSXc243DwtqjFoOn5zePnVC5Pw4xGI2TMPsyZLastRozllstxsyDIvS9XVh6O/XOrb\n" +
                        "N5FWy/FIkQOr5KEdvSk09ItbzqN1aixESilypuq5ihkgQbFknemp3Qr36PdCVj8365Ah54Lhub/Y\n" +
                        "3UV0jtBSmkgvi2nK7g2iQvTOqBBBi2TuCGrS2gJBBXszmJk4VdFaC0E7UefdB+4+RX7OzZRzi9oo\n" +
                        "Mrx6y4yqUSWY03b3cL+93s77O9VjbT5sTBGCpS/eRrM25zIuwuOXdw5XWACzC6ARTdNm9sNC/GJd\n" +
                        "tZSkamauLOh43UhOXAYpIqroJPYQkda7B9CJpu8k6PwMUbi3Lgydwpw6HFDVpCbecOZ5q0gFQsQQ\n" +
                        "ZgqP6MMMIkQBVWZTR8ra2iyiLo1ofexISskBbTUEkXR64vvd9Gg3riyqI8RTtmVu4fj6zUMuxvu5\n" +
                        "tl6bszpLGrBUKQoK9ViHMcddi57rLWVxUIMpC/Lsjce3b9d8uDVFMoaqhPKsQyEqSJazmhlPbPgQ\n" +
                        "yAIkgAiRU2d2pyoDgITThQrYhY3UzX0CTLQJcgsfzDo3EkBY0gg1UbwDCojer+fQQEjTCA+1rFFh\n" +
                        "xpzpjuh3S1JKbF4MxwSOeG6T7/HJUlahD03CsFnjuFtIHB/qWIxRxfLkkDnUkisi3Np4XJqOyZuI\n" +
                        "SWpxrDGsylx1jvqwavfX0ITRRiSVrJZVcymmmpNZUqMkmChUhmSnngBTFRUTOxcpLklUVXV24nsK\n" +
                        "VEW+lMVIJhERKSSzIPpUglzoVSFNtPu4EM1J3L3PjxGKqlIlUZAR7pE0eUB1aVM2q4DAPSQNRRbJ\n" +
                        "epiLFtWZ9Uss09G/LcN1Sg/h6WqcCYqFsUVWgZlZsmV2hGpOs7eUCo/syV241GS/nJc3yblh2eQx\n" +
                        "yzCsUlLJqVscE2hOkkUEklKHV9qxu6WqMgIKp+QI9niRPE37IAmFIp/aC+SdySeZeqxM0jWdZumE\n" +
                        "Q/oxo1OjFUhLo3WiqjqdjSmLexiVSiUaaUkzV+4VsMacIR4TDSWPIvMsLNTeWftNqz/bzerx6TBs\n" +
                        "TamSHS3lIiEgqwRkyLJMotmmGiyozjecXzckyHqbU9IyIg9S8ohkSKqqeRyo1MHMREwCqkBKaiKq\n" +
                        "6ooiBKT2cR/v8R3PRv1E1AIgFgR4AounIClRQiV1knOne70PyXrPBkPmpAwmiVOTtzFamJn7ki1X\n" +
                        "tKQSzYv4smRyMTPSPWUzKJyWNOHYDuMqqUVdYpOkzfrL6suh1elECciCeqJ/naLZlJGKmDOtJGVs\n" +
                        "x1QGyYNoYcmjjcWMyZIlkVw0SUrJcuqNEimZWbJzPbloApE1opeH38vtvQPrGog+BQkmpXEGz+X6\n" +
                        "Pv7D/dw6ZX2ehdGD7/Ej1ESRxBtCI1ygAMREQnrLcTZpVbSoV5GhZsnUabDBTOaGENNGCyZd11o1\n" +
                        "p1zCG+rC2piaeKMs5i4RkXqm14TKpFHGFMnHIaUkyJKNw6hmFjmXZKqSkqWkVoZkOgyDiFAIPbVl\n" +
                        "magYSskkez0vJNmpPfNdBiYigJbMIozukiBgRCgMZyqRqSZBPhObhfRgNVX0MUw8xZ8RIQKkHBGl\n" +
                        "STu1ciiFAukzWEw9QFgzBZFXbhMXWFqpLjqnNDRGZJUmrXlpcPchogU8pC1wd6XQT1IQxgTSxJJm\n" +
                        "k5SSGs0kpRJZs0EsZ9WciwxQSaqaU6bRkon062FJ5Kxip5yUmYEehISe8PqlXaA0D9WAAmEiFidy\n" +
                        "+6muVVvrBl5atCS9szx1wHDKF4teVBIIBVuS7BaMZgGIqXkACk3q1bWoO12WsMihqtaUyUqLVsJp\n" +
                        "mnLy5tE7EpovzUdFhEYYKd4ABKCWBUoJukDTqBYpFRMXyybUkjWZ6SmaUUW2IobeC6mqlkQsUzRb\n" +
                        "bwI/B3qAK9K57Q2QiMVsIBwMMVEpIogGSh/RdB4qRIpIqrWqqr37/DnrcLbuF8eJnpN1bwqlKCVA\n" +
                        "CtTQa2mWTSIAV80R7h5mpoZIkZkB1DZrRPJQJ8nZfYVgqHuFKd0potTT5BWcRvyYZkiICDTZMIi4\n" +
                        "AGUYBdK7S1JOPdizJF0xQTVRqLqEijkMaqJBhEkRcQpEIcyABaoiBUWJYIiY6onPdjHt/VzSRdHO\n" +
                        "HFx1D7M+bebdECucHx3Qu4KigDIiWjWTPmpFcGGfmigYkovVxcHqhFoB4H6iUAznkWDGjDOL4L1Q\n" +
                        "7DzgICmgJg6qphNQNBMxVVWYZFUnw1gsAYCknOzkzU3ZQbW03s5MaQp1AJFEBTDpuqYelG7NnI3n\n" +
                        "4lZfGE5zHd7zBXEZ1iWnPrk4T3Dq018uVgyGywVZ4GpgGEI1WYsFzQ2ZWmtNAI0BUyBC4Cxqfp5A\n" +
                        "d4kwOtJBSLAJTC69emcclPMQcWo6BqAGSkp6IvGnnPucNzGjOkGkJCpJRaTTg9492CcvXPjb6gST\n" +
                        "ZmcjWoQw2POhlxX2KKfz36hmBVgu753aEb3nGoA+mAak99j7cvezJRExQaUqoyoMAseo2d1tKBLV\n" +
                        "yip564MKI+jeEiyfwlKJCIRAKZDmp0GF/f5lK2fre4pmVVWNp4KM0k7T3lxNHUxmxYxqBFWEog5R\n" +
                        "U+0Z0DPLHxBjdi4KE5Wgn3jwvAxPeo8I825eniURDV9EtZn1kk7zKafhPVVF7xADYJYAIT3i3PKi\n" +
                        "GtIstICzmQIhnq10ByStwVI2W+iWhuZO8WKFFEe1nFBPgwP6b1kykhDvFkDkZAqcTKoNkvtQHOmj\n" +
                        "FCL65BgYTFVkTBkATc8gUVU1Uai9p0YiQonmzYyqyvA49zEJ1aUJeKoon5lF2g1YREQkBtRMvBnA\n" +
                        "05C3LFRnnziindfd/z6n7i/z2EIVEVYl1FKGRrRu7EknmXMfrRdlSOHMDBV1MhAKS7Q5L2rWCYVC\n" +
                        "JNVKnCYFkCBCJUETBOIGCVU1y2ANpjws3gZL/XSkN8ykk3/HOclZI0ztksbDubkcgCZRJIRFVNJN\n" +
                        "inujhvJdJstPbGMl+f8BsG5WOTgl5KkAAAAASUVORK5CYII=\n"
                3 -> "iVBORw0KGgoAAAANSUhEUgAAAEIAAABkCAIAAAC9w3GUAAAAAXNSR0IArs4c6QAAAANzQklUCAgI\n" +
                        "2+FP4AAAH8NJREFUeJztfGl4VdXV8Fr73Cl3Sm5GMpGRIQQIkDATAREqICAqYFWcWqm2Kk51LrRq\n" +
                        "a9/6tajVvlWpWueBVquoIMggYIEwhCEMIfM83yl3vmev98e59+bk3pOAfu/zfO/3PO/6kefcs/fZ\n" +
                        "e81r7bX3Dtp8XpABARACEkD4AcMvGQEHYAAcAQkIAaRWBABAAmBInAABEYkIEWEwEGHsy0sHaUwi\n" +
                        "inoJACy6KwIL0wAEHIFHaEBgAARhGsL9Q8CQiCDMAjm64YkZIgLwH0ZA1EMEpImiyQCASEeEEElI\n" +
                        "gyaXqEICBiGkIcwqqZWIpPmIk5wkIpJmjMVmeAKiZBhFFY8lgxGQxHIZMRK5UpNEldSBD1aQqCkl\n" +
                        "4YToCTWFmDSYMAWqIm9CrBncIfK5pGMcKJoMSXlZGFcRB4QjIY2Df0aUCikiGYYUmgkhggSGeIIK\n" +
                        "WMYajKIJKXaWfqqiuopybQ/NDAKFzBrCKocAJLNvkDsGEpEJRIREhBFDH6RLQ/mAYWAo8yAiFWCM\n" +
                        "NMKuiUm+hwaUR2JpCG9EiMFhgAHAEYliOiCiXB8unYZhOktjKngqCOt9xK0gouRbIWz0cpYMqBNF\n" +
                        "BEIyZivo7aUTEPuV4ucxJh7RcCIAUBFwyfNgSEpBFqKW46AIAwAgvQmzPDwQBxJ+AN5hLGgQy2Li\n" +
                        "BoT0KuaVhJKkQiIOOCgOAAgCD4UUiQFRmiP5HSQABB72DgRBBAGiZBh2QcMLR+6pFGOfctyIik8U\n" +
                        "NgwecjoDjEfZgEggcACAQ19vRpHJZRKOeiGcIiDH8lJA0UFFXkaTwWWmS+FwAVLQQMCwqsj/gqRj\n" +
                        "DBHx9IlT995zDXIenoyFZxkUvBXD31AxBMJykDdFCVYhiiNERzcM5yZyFSIc/BOIiLIL01Wa4LTr\n" +
                        "ysToiBRyuNFRWdZtqBgSaR2qiSgm/A36kgAAOILkeSNhBAZHvRCaHAAguXD+5JyCTK1pwyM3yUbi\n" +
                        "kb+DUQmqBHEYBOSxIkoa8j4K0gglIwgQVjhJnSicFEbiIMhtA5EzAMTSCTN85PXXWPfV15II8nkl\n" +
                        "3xHBhgP/cPPTQT4kg+U0DyUHzjljDIAr2EbIjVLIvimcmES0KGLloTkIgBMSCCIhkcvpXXXd/ESR\n" +
                        "Ht24JuxnQtoyYN8Ef/rNfdfd9qQicopUxSqVFJtEIiE2ikd4zJkC0oxkQqDQ60g3QgCEosK8Uw1N\n" +
                        "Xef62tqtIvkjhjugGET33/vjrOx0QVAPhbci+6N0TNIlJBIVUsOwa4qhK6RRA642kqFIEErmMWXq\n" +
                        "Vf3c7k1mzY7+n9+7LMq9IuL1t/4o2aIWOzuGokGRqijXFPEHIhGRUjISycYxzO9IE8hsWpScDwJw\n" +
                        "Cq3rGAJCoO3kmgVLc7My9AGVRW0AGvA/RPTe1re1ZE40Jd/42AuXSEOsccslDAA81lOFDBoH+dMI\n" +
                        "AfKQJwAHzjs764EhMQx3Jqdo1pnNoyyGdpenq75n8brpEXv9xeKy7R+9PzYnw+ppuEQaFCHClCDn\n" +
                        "hACxZEQ8hxSV5aiTbO3BAJFAEDQP/3gtkfjnp++QFmFEbMEVKwW1DlFtE70Vji4t10qznq89UTiz\n" +
                        "DNUaQRt4fMM/L4orEW367bo//GlhbFNAFIOcB4EIQRRFzjA6p4Kw2ogsXDGIvGQ4YNDAAEEkvv73\n" +
                        "T09ZOknVqGroXVXvtv3z1d0ACPqEFFOCWlCl64wWQVvXXF1ZseXbnWe0et/IpATRp70oDf3Olq8+\n" +
                        "/3DGjPkzLn8lijZEZIwFAgG1VsM5FwTBFwzEhj8EhoQMEQGBGAIKiAIwAYARSt6TRfSkdPoVLz/4\n" +
                        "tGFs8qEz58fGJay6aw0Qef1dU2dNmp4eL+iMEISFP5v/wWc7fEGvKV6rjosr1LpjGT/wzOnlPzyw\n" +
                        "b/f2rIycGZdfH5XFICIBEJFarQ4EAgDg9ftB5NHSkAKiPC0gIKCIB2Rhfz3gEGctuGaTWrjx6V/t\n" +
                        "rm9s9LuW3LTi3pXTNAnpM8cXbf7ujK83YHUaLFlJfnTmJhd29PgvqG0xk4ZGe+Hl9SjqZk4rmTr3\n" +
                        "1rA2C9ELcSlR4lxyXxqVijGmVBmRpWiKcSc2YZ5YvuKtR359ric4xZB3pOPCs+99lZg1SScYjT4Q\n" +
                        "A1CeP5KLaiK1P+B1uX2PrX8hai4AIBJffP4exvXzefvUuWsBB7KP2KjHEBFREATgJIoi5zFRPMJp\n" +
                        "+cfyjEbeGkFCQPjFX578acmYQ9VtKYJmYt6Yx9c9WpBfIAhqpzsouMjmcdbYOxNMqbaedtSZYuXw\n" +
                        "/Iv3j88v6Nm7vfjeNyGkEcq5YAQBEjkH0mg0MNQKM6pyER5UeUQJ3n/k8YkTRk3NTxolqIy+Nsjz\n" +
                        "76ja7mFBm9HuC6Df456SUiBwZkwzRQ0i8uDTv77dwLRaN1v3u82KCiJP4EK4ERGCIAiccwUyovCL\n" +
                        "rUd4PM7yK0tjP8mbdmMyeq+aUzwyM2fq2BKTh2mcGTlpWrcNbcyvNhtMcWZRRJMq6Y+/lyW/3PfU\n" +
                        "E+s0Gl13V1fK+LLM0VNkoW0g+ZWnsAOZmUyvh0vU5QoWISwuzrRv29ENv1x369qFpUtKB1IVRJO3\n" +
                        "c9ay25fMHOcJiBqVxRNwTsosnJRrAZfbZ/d911zt9FpzM/QMDZEpbrnzeuSq2pqzZSWXjR4/C2Q6\n" +
                        "gzhoBR9tnwCCIBARcFIxYdj1hlLFToKnnnv1zbd3zBs56qabF99yy2LpZdnd//mHZx+0+w1ajTY/\n" +
                        "XdfW4+jqdmdqM3tdVG3rE1Squv4uQadp7nJK/a/+8SJkKmBibl6JRj9kmhjLXAhlU5wBMsZEijHx\n" +
                        "oYhRhD/+9YPJufnJxsxJi6dv/PUj37z5erzJ8t53X3TYe1Itiafau2vdfXlJxtFpSWaCDLWhp6mv\n" +
                        "o93vdPgA4MqVl4OoijOa5s6cl5ufO2/x2mGQjsWHiARADhQQgwIyhSj+veChp14GoqcevvXo8aMf\n" +
                        "f9d125xZTqf/VEubIATjyIjO4LbeGi8Pjoi3uAOqM46OvJygG0SBMTWx8rGWcaNKA17/DTfeN9T4\n" +
                        "cibKHT0iciIGCIIQ5OLFpXFxQPzVH96cPi5nTmbO2JzUWodn9/mW1lbeq3U0OPvStQkcUGQ6p7e/\n" +
                        "rsPuFTy13T13rFs+acKYf7V12rz+Rat/8sNnBlAxQbmk8IMIwceffS3FYHx3x5HHV85N1hnsLp5n\n" +
                        "TOj3Ua/DaVabW+3Wbpfzipx0i84weUSmTpNy1bQZz675+YGmysggkcUtBw8q2GNoosgzAxA555wz\n" +
                        "QIzebWIXqfAx5JwYACeRr/7pyhSdUSsYNr20GQAQ+RPrbxyRaq7v6H3naLOgdWdrk7v9AT+Aq7tP\n" +
                        "bRGWjM622SgjOZGZxOz4HIPWfPeDT0gLGr/P++zv12U4/BOWr5152VJE5KBACg3ecAqIIgdC/P5k\n" +
                        "yEcEJARGfs+mZ+/fU9Xw2YfbXnj+GdHXVZBR+KvX337nqvEVWstv399HceC0igIFNYjTCgt6yPnE\n" +
                        "6h9/uafi+ZdfAhB+vOpaP28vKSpdsmhR2WXLQOboo7AfmDf8wAFE4j+cjMLxpdPyCm9dO/+1V1/p\n" +
                        "0moLkrKbfR2Lx08+cKZl9RWTeqzO/q7+mq623MxkEvV/3rnP3+EFnSCY9EUWU/nk4qqWjvg49btv\n" +
                        "vb3q+uUlecWaOOHBJ58dHvtB7As/iKFVOfxwacjh2sVXtrFgX4+12x0gMfDy+mv2Hq51eN06fZwJ\n" +
                        "hEpnx+kTnSqLSeVjRXmJi8ZPaOpov9BnTVWxIOMr51++5if3SCnovz59a9nym4ZKWKMEIpEh6d5/\n" +
                        "DxkAsOPLrd/t+WTz/iq3z8et/fPKxhxqbxxtsiSZ4xq63ckqONXuZnrV1OykyaOL1eq+/l5u9zSX\n" +
                        "jJ//k/VP7P/0nbikpLLyKyW9R1ldXxEiZHCAIBeVlUpRlEPB2erqIwd2r73tZwDAATY9ffunp9tP\n" +
                        "f1c1ZmxWvEp3st1mTDCka1SdAWtXZyBOBQtKxjb1dNnJ89iy1W4xCExz2y8ehMFsjgS4odCI6FWQ\n" +
                        "cyLiELMxAMOG7YGBOL9r/U3xAut2+E+2tyaoxd7e7nPNHc3tvQ6na2RaVnWHfWGx0dvke/SymS99\n" +
                        "9/XGsmkdpswXvvr2TFv76NTM8qz0qs6ejMTUO+99SKpcQThzG2qZoQgCoiiV7H+YUr31m3U3b3xV\n" +
                        "eh4xteTf72xeft8THrfLqNJYe50OrwfFIAgAIh9fkGZW6wMUTE2N33myVdBATkrS7KKiP/zuOUI2\n" +
                        "Zva8gqz08elpyZa4wrxsg8F0pOr4tNFjO9u61j74pKy6BKC0yyHp1ZAmHnHPF2UJ5/yXD9xYfbL1\n" +
                        "uMvt8niYx3HvtUu8WmdiIO1Qa+3BxkZvl0qEgFqjNhl0fX02rdqkSwiOS08rSIlPMccDCGNHJnX3\n" +
                        "s7M1dVeWl6viE7LSMprOnFxyy53W1gZLZm4E3Sh8wrESROLBYPCSpKFIj090aQUDABDnJ08ce/OD\n" +
                        "LW9/9Y1ote566ReP/m1rpysQdHhbbQ7wctBy0GqBCcxm16ekkoZPyM6emJM+e3xRXVNzZl5+HPnb\n" +
                        "7Y6f3v1khNMR82CAA+WywU0AIHkqzvn3NnEcvMvxxGO3fVpxcEr6xPz8wi5fl87mPtnSdqrdHuz0\n" +
                        "CCwQDPpA5GC0ZOQlLcnVXXvFZYdOXvjodN2ySdNb27pXrljmcfaerT3ZZ+1s4Y5xluyHN25GGeOH\n" +
                        "MRLJhDiEtuaUtmmGVSQ5DX+67Xabw4ANqo0bftXdVLNgdMnOc9Xdbl/A5SLw3LS8FEURQQByt3X0\n" +
                        "vHu64+nX/rX5UF1tf8+p5lavStz77a5HXv3wu5Od2873leXNfGTDXz/5+I2oSsBQNEhNAqIoipeq\n" +
                        "VBeF93+/4ZPKigRj0qf/Phbo6bNkJtm7+tdcP56h/qN/HgswATVqEIPE0KwXr541PzUjrdAcHxTw\n" +
                        "hnX3ATBEAmJff/JR5fGvH376VeIM2SCDjo3u0gPnXBwmikd9Gfmp/ABsxcJJRSNGOp1imzfYw3tG\n" +
                        "FeZRs2NqkeXJ9/5NxEFkqRMS0yGhq89lNhpreec0S6IzCN9+tVdeuZPG3PXR85evvi9qXkVMQLIN\n" +
                        "zkVSsg3F74caFwA+e/85sPuW3P7QqoXzxi4qMCZkvrZpiwcg0O9XGwwJWq4Ksk63l1MwfdSocRkJ\n" +
                        "XWLHM6t/Xrnvi+d21jxz29K1D2yUdIcIAXi4ngYQU9mQS2YgkEuvLl2phgqxez/8z7lr7nrpnju8\n" +
                        "Jyve6ep0+QNup15UgynI45NVBp/ulMdNIjfo9Xq9dlpBtr67ZbtLaDxxPDwmAZBUQCAS5eZ60SgO\n" +
                        "AH4uMsaGJOOiuYBi68xpZcn9PfVdASvQnXMyK5v6TAb9nmonIM7JYDu6fJCmTsKEjjbrz+fnPvPu\n" +
                        "HgCJmwKRiIrnTC628xQQxSAOvRMbZRtRTYr5M3JudFlvn5O98bopQGDyBX+2YOSJZs+dmZ4yC6Zb\n" +
                        "DCVGfXKL2G/viedYcaJPDLvKkIZI9Y2w9cYWLxVBJK7CoaXxfQEJmpdrt2lLajt7bsi2ufXjdGpH\n" +
                        "VnHJrnc/H1eYVGsYK6aY2/buEM1FSdazhuKiFa/tR5GHNoAwUjWWyp5EhPLAFwtR2eElrcUVS1Vy\n" +
                        "6Nj1of30tuSSwoXPv5luTJyx9I7soP0oT2vwJ+csWc7sPdZTpy4cba73Jo+gzm116rIZuUf+sl46\n" +
                        "E4EYWv9EDjNItq64iFVETKFONVBzlvUbXrLn/rwhZeaykcdeT80fYb/vmg33XN537I26hupV15cL\n" +
                        "WdmVh49tOqr+qpmDszXAA5P03cuWTen25dua67b+KF6EIBECqMI0RPBhUfgMxUrpfIrSIb3BpaGo\n" +
                        "IeTPXz18GwA4TlSf/fzVx9+oZ2aas3rRoy8cUF/5kMeuZl/v3vnm386744vN4iOTHac8Zm1L59fB\n" +
                        "4sSkeKfDkzPtypGMN21YFT69QArHUGVMVIxjA5XS/0vbeHje5Buyzfml+dnJPY1bt+ED73eDuv7v\n" +
                        "r/acP5mUFNxV4WcJOCMpq3hRiVsb766rfuztY4um54tx+tn5yVs/2nfPL2/QaVQpP/0PQEKSNhoH\n" +
                        "EaO4hJKzMiCKhCA8+qvBRwWGrVzFeuFR8+cUThuVnuBxt12INzcfe7cy2Nd19PO96zesES4cKs6I\n" +
                        "b3fCgtWL9uw7s7+6ft8Fx4OFvKOucRLZjGpfLwchPaG+yTv2yqWBthrBnKi4URE7qVxlQiHzB0gj\n" +
                        "woz9v/7Zwvx48lV/cpqVlpitb7+XPEZVdUy17K5JPabLqoM6ser4bzd/l52jKyud0uHs3/3NsSKj\n" +
                        "OC8/0N0sjhpfSKNG13f7i3u/mfr3Jr/eMkx5f5jQQUTB2FMKMISVxw6KiLM2/FVTlu869tWc+zYa\n" +
                        "7f4pl+niRSEzI+BNna0KdDibOr747N8eLUuIo9MVh/ftOr2mUCxNCxpQY7OhdWRx5rSFvbu/yV7x\n" +
                        "yzM3zwUIAEDshuVQMWrQTzEmp+JhpbpIuh4+HvzFugVrLIfaDonZK4r36hdoq9/T9enr7Sy3bOzZ\n" +
                        "mvqqaq/T1j0+Nx1M7JOK/gemiG1xWdk6hx69p84ZR182u6q6Kl99IWPOSreLj73/ZcW5QifLY7Rr\n" +
                        "QBqxe39KNESfKpe+1/o8zvbKwlvW++0j0xeprSmjWUfDBzt8fz3g7Rcc/YePHjvlXzxTdVWpZeb6\n" +
                        "B7ICfRqftSdrGuaOO9tofX2vI38U+Y9srjrfkTx+hmtkadEDL8XigNIxXhzkl2JB4VS0EsbKTtmn\n" +
                        "jYv7+I7pNVs01yztvWZL744tySrTmocemzU2w9nUd9TqTKKWLOeFoquXdR/8ILlwVE1AW1dz1tZ4\n" +
                        "uuTmm7VeT82xM8Z+TV7JaCFzzOE772FBPwK6utsiWiSdaQmjIcpRkiuViMCG90vDMEBqqkla0bJv\n" +
                        "qw/9qU1b0A5NhyoPvrZh9IKiZK1qTpbL5sC6zJ9+c9zz6a7GL2ucd0417axou1DT3PrBiwsuy5xa\n" +
                        "rKs2mOfNGtdyprnRpN32y9VEoiElI4IlGzR79N5f5KdAoOBwKSb2DQUi8ZY/Ls+766UGYcz9d7yo\n" +
                        "Hl08dVnJTKu7PnuSzeZyu/2fnIO49rOIuqq6jrml6a3nmtQqJgqesxf0CxcXeYSkyeqenRWdOjE4\n" +
                        "XhVf/tYOlM69hoDLVx2K0SOiaUTf08fJofn+8pE3/h/z8ZdanrxrFvWN62/Q55fAtUtsF+oD+sTT\n" +
                        "vbrHZuDsaf2+rtNPLHCqe1oCdnFGgr/XZli+etpn+7qDpowayzhLauCNPfUOQ6/94RkM5ChG+/3Y\n" +
                        "6o4EQaDh9v6GzGHC7/M27Uv79sVDtLwguffuZ29IyPSmtm23792epFfV17eleYMX3J7Tp8TcBI2D\n" +
                        "6YxZWSUrL7cax7R4fKfOtfRb+9wBr/vs4TO2zMWJnszZi3XJIziQ2ueSzTLoiKWi2yUiFaBCThUb\n" +
                        "MaIys0jdxdZRf7gzr8z36ujb7tpyJHiwPr+jL7D1y1MjnVvR2jBxnH1ySrBscoHL2dPDy7hupFbN\n" +
                        "p85OvjzXANa2Uo27cvfBZHXc9TPYsutKW7s7mo8e33P3NQFNZK9ZwbVEQF77ISKkmIsPQy3ho9MB\n" +
                        "4vHpBWRwChOv7t+3P3vyLVV9jkNHXFMK9D6N5o7rr8gptWx89tB4g2/bCUOOvxaxOc7E6043XZZD\n" +
                        "QacuU+9LmVLaw1Xnj/UsXb5w9Km3qtWF5c+9M0itwrgqZtny1kvaUFYEQiYir/224Y5ffOrQ5yfv\n" +
                        "emlp5zfBnt1Ty73dF5x9FV8c/MZ+y4OrbOoMY5o6KcnW3F3r8zjnxjFdysiSq4pTV9368cfHO4Oq\n" +
                        "GVOm9dTVfvptxlEvaOOMQ3EQBx+2kT8zxlRMUDpqP/QSXC6Zbc+/uOrDv820eZv2H5zx1DXi8Y3L\n" +
                        "Ifnk0QT9RI+qYX/j4f2d5LRXnvG6HZ99q+9XxV95dYmut6u/36UbW+brbp5lAVPQ1rdneyJrXHHd\n" +
                        "DSxzDITXfRFWSYf8Y+N3VCwnouhkRISLF+2kjwXwVz90zSsHTy3PEX5U1qKZdqOtOXCqNdHffKbx\n" +
                        "syO6cZqlI2wv7BByC9Q6k9nDE3tbW3qSs2aXJpF+bM+5b4Sscteef0zMDaRMnXvoTF/QPPnq518h\n" +
                        "4LE3rlBWC1WsuwVik5FLScVC5KGmOyHnd/9x3+JbDHa6Kti8tX7i3Z1WdwfLTL1tzcrypP1NcG+5\n" +
                        "avr1axyWnPqu/mPdZuBWtzu5q775wJ6OxL53MEX9xR7dufqu81U1RdmO+vrjACC/YRCZfShuSu+F\n" +
                        "2AIPxyHLRLHDNd064TynBdet+fytHTdemSN092yt4fsuXChOah+r4pMnsCO9C32C28n0mviEA3tO\n" +
                        "6tF1od46wWBXqY1jk4TOPlfu0muOVdcfq2xYUT553itboyQQTucYDPZOUfwN8phDekHOVWxgHTyU\n" +
                        "RkqtE4rYBGPxvx1i6pKZmF3RefKA77D2yd+soMNb2qvY0UqxkTXrRpj2nuluEzumc7vOakWBFSdp\n" +
                        "Rvyo/MDeCm1OUbvLG2fKXL7I4CCzvGRIRGG0WWRpJA9/8mcBlU5+DoV3LPQHTSpLau3721lB9pb9\n" +
                        "4G9MsGt8nj0fwYK7tBm12hOV2Hj+k/qCLLPaf/6MOg2mFPhK4gwdmsKqfacLxkz68h8NS7K7zH3u\n" +
                        "/mTzFRs3hasKEfwiVVCS0Rbt/RljFHspSBLFJWpnb+kNLYe2H+73zO49r8fW1GK8oTDfZtKfrGj6\n" +
                        "7Ovq60r5gVOa0ZP652i9Uyaqm/p0jaLlTIXHcnlOVpE3JyuvIPfcu3u6ZyY51QGNPj0pZqNCHsUl\n" +
                        "CglJkG55yhIqpYsPctTlkVuxj37flmBCwdHG7qkj4KqJObntupavT7fUJKaMnjilKKeqKyd+dFJd\n" +
                        "q+u9w+rpCzJUYzKNGl+WnnSiO96U0Hm6dtaI9lk6U0O3b9GqucgZA2IoCsgZiAxFhiQAIXFGHCAI\n" +
                        "wBkSIQ+94SJwkRFnxJGLsSY++NJSVNMgO2N7f/PQtI53KN2csvhmFTPX/+sv9nrb33d5l/7s1hxX\n" +
                        "U23Tkc60Cfa6ioWj3McvxE1ee1+jzXp45wET+lM4fLv7zPXz0xq1mYKGpbS3ln95Qc7B0EVhCCkU\n" +
                        "EZOlvhQ5PE8QuqYRrVTSNeTQdbJwP5DbFwAAI+I1mx5duGpunDNoffZLln2wzpuyvSmzLE69xNRr\n" +
                        "7ny53gGZbnjuc/V1E7wHWwrHT0xpPHf+xMEDJyud3ZzW3VVuOtVmmFye4Ff1nd45Y1sT52Lklich\n" +
                        "AEcE5NLVJMKQlXCSrixIBwBCh16AASpd7ZV298KXrKR7mQgglxJHxFH3/dZXtKT/qz3eZ34HcCKY\n" +
                        "kJPKvdYEhPxuvQcKgtDKEn9SaB8jmLyu9pMNpO5qiO9oWzRu9HKV9uXXd6Xmpba32u1VFQf2+j5e\n" +
                        "uYQTioCckAiJIwFxIERCFAAZIePIuCAAExAFAMYBpQdJetFKJaE7cIs3vJZBir55E9GudaUlT9w6\n" +
                        "Tr/vn+fHrE1ISbVU/cPW2MhHZPvzJmv8dv+5E/VnRXuGZlZib2urgcouNwet1/796B0zsvIzR+T6\n" +
                        "jvpBu+D9zoj6RuVOkhKEL/+E0YOBACJtOCk4XGKhnR9pIJIcAg7crw7dGgpr2V8X4r4ulqydm8+2\n" +
                        "prlSNFNYnxsSEv1eMb4dtTWBTPPIZntt/wGruS5gOf7BgU1P3TQ/9zy3YRui36latrPa6nUhInGu\n" +
                        "1WoDgYBGUIlAjICx0LltgrCMiBhjKI+DpHSeShGIIXBi0jH3MNskqlSciSgSUM37f46veOPEGX/A\n" +
                        "pV5w7bm2pN/6zh6qEes7xImzzD6N558P/4396IqZcQFb4bzJ+z8+n6C3qaz9Ze9+ro0zIqJWE+fj\n" +
                        "QSaS0WjUqjUMUatSR3CNFA2GSvaGI0PubjH8c9Bdv9D+BHAGb62ePCpPi80n+wquNfY3FfJDlW3T\n" +
                        "0yeqqXW/WcCPv4wrGsOPe1KMZktdV38wyHxG0/qf365Ny4LEZKY3qDV6g9EsCIIgCBqVWqdSA4CK\n" +
                        "CcJwqz8x8jJm9Re+XUah/A8lLeKy+zXSggtFkpqAIRJb+sb+qg7VDTvjWyuq2mtaKj0Tc8Z1ORoq\n" +
                        "tQAV9WkJ43KOd8a9VumoqO8RRPeRBvuiVAxae4kR02o1WoM2ziAIQpxWZ9bpDWqtCplOpVYxFk5V\n" +
                        "pcyXE4lEohQ0kICRdCUpRhocL75xONCBk2Q2RBgAqqmvDva2eetP1J6reubNbzrcQV0w6OOiTq0d\n" +
                        "lyA0C7qC5IQCizk3J3VEmiUjMSk5ySJm5upGjs8YkWVQaQaznA/YOgGTFFuSPxu0hEIkVFAqhbOK\n" +
                        "oU0gyRGHHDaF9kdCwsGBjU/pwe51O1z9jr5O8LrA42BeFwFwrZ6rNWBIUOmMgiAkJ6XqtTq9oA5p\n" +
                        "CEkb/RAKCBTyJUQEyBERiDEk5TVtTEU9+m4GA5TCKnBJiuFLs1LFkSgswB98FpZjZGS6eD6qCAqL\n" +
                        "WJAxXnopaaeEtvQKAQi5REm4SsblI8CgxSeTLd9IsaKMAAwQvicJIoS+HSJRD/+/F85gIKDLinoU\n" +
                        "drgRVBSXNeFnLmkfhvOaKIEPe7BwEIQUfgATxkjp3h8ACIAgqan0H2AifWQX5lAiVZY7DgiBQtlx\n" +
                        "KEfm0TjGugpCAGJDFfgin3DOB13gA4YkHQ8YQhqIyJGHYgWFeK8oc4qgHvFegz2EdL0QwjZGcoFw\n" +
                        "ko9IMeOTLN2W7J5CODHpMh8P/6cKhau9BCBdYyQAMfyCZOok/SWZbDHSMDRwiJwQDFMyUMoBDgQE\n" +
                        "PHKVBkImyol45NhRiAZpNgrzC2H4KP7/Efz33Bj4fw7/S8b/JPhfMv4nwX8BO5r8ErpR++IAAAAA\n" +
                        "SUVORK5CYII=\n"
                else -> "iVBORw0KGgoAAAANSUhEUgAAAGQAAABICAIAAACP7sdOAAAAAXNSR0IArs4c6QAAAANzQklUCAgI\n" +
                        "2+FP4AAAIABJREFUeJxtvFuvbElyHvZFRGauVZd9Obfunu6e6Z4bhyREkzLNIW0LsF8EmqQM2O/+\n" +
                        "bf4FMmADhv1iyIBBSgYEy5RMUCTNmeF0z/R0n/veuy5rrcyIzw9ZVeeM4Hoo7F21qiozV1y+iPgi\n" +
                        "5Fu/Mya1kDCziKZmKqRAGK5K0iCiSoEEg9SsIKEQUSEDhAoAVQ2ngCoiCndCiRBTCwYAgYgAAgEB\n" +
                        "CAwAAMITpakmgKAHVBGBpABAUhUiUoMSDlMRUw24UkIIALAk4QFLKhFBJRDKErGY5YgmYkAAEDHS\n" +
                        "AXUwiZIOiIiSTSQBfYmICBGS4lrVE+kAKDltb83MHCpBs0FEGpuZmKiIkFTViDBTEgAaJKlBQiLM\n" +
                        "UiODohBR9v0TTdVADTAQ5iEpgwZAlJWeQJFkkCoBp2iCEJSsbI3J7Oh1SFkpCxtEVUAyQ1QAQgMi\n" +
                        "ACDJnEHSRBuzqqZwVyP75SEQiARshNZwTRYRiWlBkBjVFpgEi6UFiRFZhxpzhpIaIo2xlXGKGTCF\n" +
                        "JEhabaDKAIQoxQAECilZDQBVQiNRHQgyKaiigVAIjPASIImkGqAJQoiiqpDoQubuAFJKEUGymAKQ\n" +
                        "IICiCsBBAEnU3c1UINekAC7YQkj2G9bFMCJExMxABtllgeQo0v84yXiEiPTn/sEEmGr/yCASEQDy\n" +
                        "e+/2j+ew5p5TIjkAALbIpzU402a7bUIACqGEig5SXKqJqilP69OiIhDISTX6KiO0n0L/tx/NWfIz\n" +
                        "6SImkvu6VTPee7wn8HJ+5XQBSTMjGRGqJwEn2a/s32Zml9+9vNhfByCEmCIYoEIABGii7HYj2BcT\n" +
                        "oIjQI0AA2RL09D2XlQAQKIQRkXSwUbUvRdVEAkiD6mkFpEoKiKldVPIiKXK+n5ednBdaEBQRATzi\n" +
                        "pMiqZIho3x8BAnreJwCCydL7RxCnVenlxQiniEkGTpZPVWu44SRWl9v2H9wJVXV3EUFQunT37yek\n" +
                        "CAQMOkNEkppIiggxpQfJLpICpHHIp5uj6STw4iJJxRQmhv4bF9k+GUIFoht5Yb8FpKqEKCL69X2V\n" +
                        "9usr7jf/srH+h8H6u2eLrpe3zg/rX0ImVX1PtENEEvX90+lCR1LktKN+O0tJ7x9olyA9i8XpOYQS\n" +
                        "IuZeAUjKoid9JyWlkgTat3daKE1Uk2kLNzPJYcyhbRALzc6WOUAcQAMTBQloIooWTClIU3Q3pUaQ\n" +
                        "CgOhRU/7cTBBL/ff3fttN9P3hULV3t/YxTz1185vJQeVaEIRyaGuQNDMxFnpxbIjBCJUo5Bo2khC\n" +
                        "YDD1pIQlLPQi1iDqDDUGBrMAARVL9NZ9eyqpEDAzUYQ0VTMGxIKUpFAZMYZAoGZJBeKABAlVTeSg\n" +
                        "afIqBWqWScByCFQqIkPnaElT4knvRIwSxUwI0qEiREoJwVIKPc5neLrDJN29pCEi+r9dNlUV6I4f\n" +
                        "mRpGpSSwEVCRoAAwMUBE7KJxgEesZWgdF4ggn+S3IItIIpGQRCjBEGFTWMChYgA9EnIUHfpNk1Ry\n" +
                        "WEsBYNQEwoWNMebiYJAFKpYbQlUZIarhGCx1rT5thg0hWTPJdRnipGgUsYgmmk9SAe0SZGcTLilF\n" +
                        "c9Eu5hGShIGUAZiqEwKBSlYTEQIaUoVdKgfVqroSOEIlO6OrJEk1CMw9VKCkiBhPFs/MWmsXleqO\n" +
                        "W0QAg0LCGiOJnSQ9aRpLEagrCiylRDKRJuoKKNCWVS4AE07a3hpShgDUrghuZmCT08+4BDULECIC\n" +
                        "tiQKFQQBL5SZLVO6jioIM9IhWcUBOpCz0kNpIcT5HAEY1J0pSVdbVXWP0YykqQAcNGBqTd3C6Nbv\n" +
                        "B5NoJZsaIixrkCEO4mS/cumexhhIKYlWgIxMq2hpUCOFcIaRTDkNVJHmlijqAaqZh3cfrJpVBRBV\n" +
                        "FQ+a5gyy23sRhenYb8jFonczNGqqGgI1sgkhlikBrqgXC02hiekSH014tCtX97Q55l0EVZOZJBHJ\n" +
                        "mxXrtFvu91e2e2Ivb9iG0n9OVSlUqpioGj1CxZI6a198Xwspl39FckTQ2A06GYCRFAkqRYRU6QpJ\n" +
                        "Y34PQBgFSGoOMzGBKFQNILkaxxbt4iO6p4OZnD4MVVFNRIjCYJbU28kGZ5Gq6kCBLRFmpqSaAlAy\n" +
                        "IgiayO0dn7yS7cGMmfdzmnZZmAWxW8JUWjz90Se7Xz3k3X589vgpVnnc8nlrz+1e/ath/uLbXldG\n" +
                        "lYhI3etlM0BMMvN/gEgvbgRAzvmEVMiL2F7c6AleACml1lp/5XJlQkoAVKTDv6APKQeiq+Tp93iK\n" +
                        "/vp3iUBFIRAxADAIRPUkXgEqRRQCHVuVi0kSsbtl+Lvdk8O4arqyfPNobM9f/6+v/6/07Fv/5d3N\n" +
                        "7v7to82Y9wtU7IOP7//t36+ub3R73d68Xj950l4ehkQt9mhcX3P12T/48/nVT79vh9us7DDmBPqS\n" +
                        "u4q4Gc6/21FuP5QLmDCzCFc1VW3tJBlmdgEu/QQ60Dl9s6p2nHj6CskEFB2dSko9jOzeLErKYPeI\n" +
                        "SrrIKVASQdLUgxNAtItkOHIBG6n4u4fVz+WKOc9J6hxBMnZf+19ML+2DJ09tc3y1M8Hd129vN0Mx\n" +
                        "m3f3aXvrz7/Rh/vxgw/8F798dLvyicPVh06ph/12tX6Ubj//gl/+7P4n/9Fmzu4EJERQ1KqInpVA\n" +
                        "RM7P7M4knGr9BBMZl3OB8LQjAMKOqC4iSdJ+/48/6yfab4yZppRFqdq3fMLQIqZqZ+zd75iq6nsX\n" +
                        "oMumntaJrGoq9a/v8a/21/fjpmo+LLms0+FuFXWVhqEeP4X893/5vF0//rne/1ZTIvIwrg1e29XT\n" +
                        "R+X4sFrnzSqtH1+LWcql1Ycch2HcSqvRmnq7su3jXx51kYfHohCFaEfhybpZ6KtKKZ2tsIrKCTYL\n" +
                        "1BSgWr9KRERURCWlBAFA1R6PQ0TtH//TT1RMlCIXIez7T4CYJVURMXnvoapm3ftJN7QXA396hmSF\n" +
                        "zrj/n77cviqrY8j9nRynuH+wwyERRbF9/GHs3iRLf/zZ5//8r/5mVdb/KK9c2npT4NVKWi3Hskrr\n" +
                        "m6vVSlLOpT7Is1WZFguZ3rydD0evjchIJUHX99x+Pd19e6CKqELBc8rkHE4EGarWNatbqItRPp8p\n" +
                        "Rd4B4xPAUT0dd1b7/T/+jqpctmqWRRQQ1TCT/ppIAGpmQAOgKkBHhjz/lkS0DIHooKJwf+H6L16P\n" +
                        "S9nMze7f/vbvfMe/eJGTDKZrhx+rXK+GtpgZlvn/ePNQpng5Lj+YWEpK6+1+qsO3Pkzw4ZNPiyKl\n" +
                        "4/jR7fHLu+PbhzqHiUpjrRVoyjY8epzbFJJuvpruH6coAJIQZiehUL3EW92EQqS7qf6KnWMeBQJg\n" +
                        "Dx76vrrNMjMS/bAUQCljPyPSVaGaSJh1QTuppHuk1KMznKOq0zXZkkEULpTjr+bjv3hhe7ep6cOd\n" +
                        "7eb7v/55GQZ1jkUe/aPfxMPb9vphYdt8+mk9HP744yf/5/OvbnP+TATGdjjKzZP1rZXVdhAfhlnK\n" +
                        "+PDF6+Hmo/bmpSSVZTlIypvBHn881F3zSFcoBzez8svj7pFwZUAQEmfTfglXL3+kZN0edTEy065b\n" +
                        "pwgi3CxpT3SqChQC+8M//V4/P8BFeJbJdBG/C9ZQ1YvoxjlaPkE7wAUSLiKHr47tX95dT3LDtrx9\n" +
                        "k+fg5FdXmzwMoyVQ9e2Dads8Xss8T/u3m0dXeSj/y99/9fXbtt/wQw93mDU57K+vckqt+rL7ajfP\n" +
                        "7ncvdRjL0ytDKTfZvv3B08+/4/d3OpT91y9icyPeyjbZ398/fHYtSbJZUmuMbpQ7IOiu7X0XmVLq\n" +
                        "m3pfE/vuT5gJHRWE/fhPvttVuucDzHI35OekjV70Fr+eGDidlKlCIErQgOV53f9v31y3FHf3nOeb\n" +
                        "R8/S4eF6pas8rFIu2cYhF1BS2X78UXu4r+3osZRR//efvrgO2j0+vpVcsupy/fhKcajg8ZuHw5HL\n" +
                        "PAE96ooj6IcKDPu//0miWzvKD3/Qdq8tuATN0vAP928/34goySRqoj11dXFtfUMRNDP32oF3948i\n" +
                        "7K/3bbZ2sTywP/iTz4HuPsvFZp/x7v9PkugCPU5WACIeWcWAuvDuf/6HGx/56n6MOqjd+nEMi5zz\n" +
                        "eiWrMoxDTpLW6cn3Ppufv75++mh5eLM3l43953/0g7/8ydf3U/zmtzPcb54kxNEslofj7uAzBysp\n" +
                        "oqWhtAUJGMuwmiYdxzIqW9RXd+tBLEzWq+H2ujnw/O3uo1XOWUQInnCY4uQGOy4QAEQKhZ0lSM20\n" +
                        "u4KztqZ3YWYP4szyBQFcAO5FAS/HJO+l4k4KSKrC3b3F6//x737rg8f6dj+2edV4E4EZaLEWw7Rg\n" +
                        "X6+ub3F9ezfhm2++wVYefvmz8eOr/OTm1TLP7k3TzWObNqslt1lbK/7Q+HJq94elXfvwg+/Fs48p\n" +
                        "qTx51NIQY6nXq6v/4vP69Knerq6vpM5teHaTjvsh9Ha07R3H++q+uDsQKk6J9xIQOEX4qhnl4vhI\n" +
                        "71kN99oTXu7e9VTP6MLOchTBBlDUz04XlwTuBQG/L3cBQZDk/d++woyf/z9fb+q8rsTUdG7jOGq4\n" +
                        "wMacV1fr+/2uvrlblVxa2t/X4Ye/fXh1X67Wy/r2Vw/7Y2t+bG2TfBPxaP3K/YuH+qZcLSvz2w+n\n" +
                        "l29uVkO5uiqr9OjDJ4+flJt/8kH6+uXtp6t5s/WhrD9at/lw8/TG377SaB88vX76fz8nlSRCwnHJ\n" +
                        "6IpQAAHslAvo0EpAJrN3KMziYqNPlueClUiqmmkRQfjFrsslEY73kpMAjEhgtBqBgOz+9deP5GZw\n" +
                        "wkRErjbld/6b/0QcKGW+vV5W68Nx8tbk0Vq0HFOsP3u8O/wC66vXDwfPw1ffvN5NZGCO++Gjp8dh\n" +
                        "7R/8Xv7Od5eHh6Vm/5u/nX7+s7YqZZXTXDf11TBye9Crb91YfZtxLFfjMC/lODPmdS4ljSvLT/LV\n" +
                        "5md3F9Upkk4JxbOKdBG7WPoLSDwhJiQRvid0tB//6fcuDkLUQSVP6eJzclYuxktEGEwMCTA8CIgG\n" +
                        "48Wff/Hb43d2//B1RstzmJlVf/3XX4VIg+bjFIfZg0JdnNNh0soXX7x48WbyT25F46sXf78/vN1V\n" +
                        "+B7f/qyk0rB+dphTscF/9uXafbPKm1w2U11nFD+MT8r2dz/RcVAJvb8fb68VoZEw2vT8MG/08Y8e\n" +
                        "L4u2adbDvPt4Lb2qQg2G6Ds3BWGHmAzpJvximkWklztV7aJP+v5ZMN59oEd5lxjyEp2jq5727AtJ\n" +
                        "IpQ/fXj7cl8ADahqgvzGf/fjpqlOMyMQMTCeqd6ybfYH8WVUGVW/88Ezvjj+xb/767zaDgN+9Al0\n" +
                        "wMu3y17XDxN31Xl4UR6vh8dXNm63n393+6PvI9hCBav5H16nD7N+sBp+4+nmpm3G+foK47i+fTxk\n" +
                        "4/1ffQ2fRpOtrre/OvbdBluCkBKUno0TqKpG9I1SxH8NWJxSAH5RLPvxn3z3vVLNu5zUKYf1rk4l\n" +
                        "dCDEFD3bKyKgQnj/V79KX9fPP3929/Pn61Kqtwx9/W++FOcwpryfNsGrZKWFUDNjtNxK2iYdt+v1\n" +
                        "fv4n//QP/9W//5uS4+0kv3iug8rmkbx58dVvfu/TJy1vFl4/fTZIDPv7PN9ltsf/+IclFGUc9g+x\n" +
                        "yuFLjPrT5w8ffnJ7mBc8+wAxjk/KtJ+WOkdr7WF5+/HQgzuSVFGFxylbyVPFu+9UQT2Vjc6mCudo\n" +
                        "HID9+E+/e4mh3herd4acQC/V9Soqz6UqqkcNx5s//8XVMtx/8VadqG61bcSau1Biatri5nrMn23H\n" +
                        "+0WS2GrweR6lbW63sdvbzerhyze/8fiZ3ev1Zx//4udvfm/U33b7+DjefjOtY3yyWt1s5dEnzx79\n" +
                        "1rdWn34yXm+zZdQ327EyrYcBUg+CeHSzBtUkSAyP0+HhQe4Z0PBoh8Px2diyejQxa0LpML7naxWq\n" +
                        "6XQu6qJQ0UtBJE6KFf1gzuWvi3eLEAICIQiqKuERSkolE+LkE0NEQmCcmsxtKFvKw1AK5rnkfHQn\n" +
                        "YB4j4vbJdbs/lJ86NmOopvnwZFM069iW+83W7+6/9ye/+Q9//ovP8jZept/+3R9eQ4YR6fGTcf/G\n" +
                        "2LAdSym2rfLy1Wp8vSoDSh0+feYC3a58uov1tcxzzId0s0WT9cwXX7+6vclN1vx/v5lwXI/58a+m\n" +
                        "r75X1BThhUYJmrInnijnJFeAiRJEY5wDI5Fz/VMiIqlFd4vvUosqjMhmizfWFlAKhTAwIgQGvoNj\n" +
                        "r/79l5h9XLsXTeBybH/w3/7Hf/k//LsEJGEq5biftk9vpJh7ZKmb1WZIYasiq7I1bsu6/sVPP01x\n" +
                        "uC4La/rYbr6eVptV6LJ6moerUa9WGHJjS9+5GZL5bd48LHqdplGS1FhdcbVunlJ80J6/yExtHG8/\n" +
                        "0unFwT7Iwy/1hqt22K+PC0AEe8FZe9FUAJ5q7KoacJFG7yaXF93q5YOub0mQCbr7BZf361p4CvRE\n" +
                        "J0l6hVk4unPoGCJT4xeTCQ612rJky1n03/7zv0ybFd4cNFnO2VZ53I4JXJuthiuTuh1yXK98t8vH\n" +
                        "aXu1KjdDq4drKm6fWD1sfvejNDar1dY36YnpajUvzOuxlSQog0z24bUOeaOVuXgqOqlFuD/ER9f8\n" +
                        "8q2+vVue70JTDWvWJMpmVV69nUgkTZWt7w9InWOgZy+PMNFzwfNsswAQCjpJQE9Fjks56CSTkKAE\n" +
                        "QiJCTSIg0s+oJxLdHYGK8BqrtGrLshkLgyZa4d/+7qMX91MKJ5NaXkK3V+Xx73xU/+oXZmnxlo+L\n" +
                        "LHU95M2VlCu1zUa36+CcP/okJ+XKdN7Z42dsL3STbKJKSrbIkyscwNUIzXKYwb01SjKXpKz24lW7\n" +
                        "uZlGyuEY38y7h6+vP3321b/+2ylitcrXL473zzZQDUhRFWekHBE9GfN+rPK+vSZJhoiRFNL+4L/6\n" +
                        "/BJzRzQRzR0PoClhqtLr8tFD0XNiCAogQY5/9VLmug7/zo+e7l7HsjvI4u3VfrtdjWpmFuDV7ZUZ\n" +
                        "9PVRxPJmRcnCJdtw87hsfvixZbdHm/KDx8OHj9LtKj0q2Ka0SUgLh6wl0ShXWVcFBVpMOi5aFURH\n" +
                        "SYJK08z7vddFD+FvjjPgsx1+9SJ98oG/uT+QVbH7YNMYIhqAkgKRZKQI+H7kK+fHGRTI6TQvEc/Z\n" +
                        "TWoEFkAYGdZ1jWRr3rlXEREeCLi7eCwtxlTEw4bxm5/tTVJZl1JMDWLpox98MKyHESH1OAbF1NZZ\n" +
                        "LKeMvNlsvvMkP7rCckg3K300igjSIgMbDpKMY5F1sVGRTK/WLAIbYFusbjFeiemr569ktUbZYHWr\n" +
                        "200c57TeWo79z563zTYPSQ97tKm9emU/eDKwbeYaERZneRE0YXU/BWtnZey+7oJCSQpAOtk6sApz\n" +
                        "v4CrUZFJqMX5K0iCZ4tHCuQdoiUObRk3V0vj8XjUzLJetcmzWLlaffG3vwg225Zpmo7zVGsVGBF2\n" +
                        "c5VSKTmXR1t79EjLqpS1QC0X0tN6pTrLZouhYLxCugVGcdAGWRbISsLg5fFHn8I2WF1jf2Aks4LM\n" +
                        "2PlqO2ieGpptDKuM3WH+yTfl9ma4Tiek7QGgtiBZAkHBmSRyIRu9y7vAPAIQVYtoKtRF39n1mexn\n" +
                        "fQFcvUzUUYX2YDsCQKWFYJG2mw5Tm7MqHclyXqV5iVc//6aKHl7tjy+OsfC4nyICV2uhYAk1wyoF\n" +
                        "wBqtZKf69SDIpgOQKSXkiFpRBclhG8YKIlg/gk9oDqh4IpV1QS4pGpVL3euYpWidD3Ff66NSfYjc\n" +
                        "IHF88QKHueMZAx2uqj0C6rIm2p3br5UaSVLCJAHsxUN1huGUKgQh3loS+ikJw5Bk5R2Ij1Oa1UQt\n" +
                        "qrvHSsK0AYvE+mb16KNtvlnN0USIY2NzB2OOiNg9TNOvXgYiIkIQN+MsBlVJJdbF79yP4rPWg93d\n" +
                        "SXtLtAIdEKCHiGGesewQE0iYQlxaFQ9Uj2DojFYtg6ZpX/zzp/71q/3u7dGsYsHVKEQEIzwipHpE\n" +
                        "QBgqDppLxMnfXRToHT6XuFQxVFWD0YtIRYCQghRgl6l+TL1ATz+/0rw1OdWPRhxiLmYApmmqTRth\n" +
                        "KaVxpesBq4Js5aNbiBC+hO8eDtM0BTy+fuCxzbvqB/eXLR/Fq7Qd0VYbzblcI8Bp4rFJOzJnlEQl\n" +
                        "jwfYhHpELL7fcT4yRfOJk8V4My8RxVo9tp/9ym8+mYG713uVFCqthXVsCYqaMtwtmhtRGYB2ygOD\n" +
                        "l3xUl66O4N/FhmdTTyEE9LMcCewdL8FPh92jwgsgg6u/PJhHrjLkguDYIMR3fv9b+28eluOMpaZV\n" +
                        "5jT5sYoihZsgGdRplhhNGAq0vl6D+5xoIAKhCrQ6OzMCRkSlCCKkOeZQs/qwPxJSF19qm6vvjtGO\n" +
                        "lZwefJmmV7vZTFZPHh3bPGt6/uG1iIAUaqcrmJqYGCS013vYxQlUspMRLy5R3f0djU1aLATVLrCr\n" +
                        "5wnfxx0d/r4HR5g/vKJJROza1I7z6JzpovziX/60TbOIYD0Mv/XRcVrmpCglxtEFh6kuk89eq0cc\n" +
                        "5lormi+aHc0OtR53cdi3Y/WK6bhkX2I+cDdjX7U1mYPNW53r8eDuuc3h3pbq7qEaNeoOrnH/8KZN\n" +
                        "dfsbP7q7e61iraS+F0CbonOSwoMtAjSHdqp1T7Z0ksjJxp/sV0op4T3uZRG0iPfIotEz9vqeMhN2\n" +
                        "RrSQkFKSj+p3NWumcDdPOujx/riKgAtU1k+u3/ybXzSnv76/8zlWa16VpGvZDHazai/vDk1Wo8pt\n" +
                        "wnI3r4fx7iGlAaExs6kE89SWEgYsZKgXEXdvMVOUQPFgbaQcxadlkbYsNUeLeoDZSt/89d+mm+1S\n" +
                        "l4cxA3IiNzgkCekB0NQAUdFg9OSfIMieOI2LrIFkvCO8UnSOms7wSkVTSielAwl2oXSP1H1wCMAI\n" +
                        "5uvM17Xt6y4ONmLFUEr1GES0ycPXL3POqDEOFq01RtqsI2Iy2N0s7lZKnZtXsYcJoXWqYeI00Vbh\n" +
                        "8MXEq5GhmvKy7C2l1w+43brQQo++eHPl+uphp7m+jjTGMnmtLQ/eljRm97pkvL0eLwzSFp6kCCkG\n" +
                        "qdTESgihUBFpQRUA7Ll6ORORTWh/9GffP4FSiUxDz1KBALyd2OSqhrPVU0hz4pSVICm2WtkXr7DA\n" +
                        "nKmFT4vPPmRrHiqUhvCWU2qtpdW4HKcUYqOl2vTWhKj7KSBoU7QlgirOZVELl6NGinkiW1uaoLZi\n" +
                        "yem1psGi1rbM1R2tOef57k7awqhLs93b414wNfdpQs67usQ2/fJbT089HkFmJWEi7C+dvF7Xt04U\n" +
                        "9YCYJPQuD0CU7pr6EZi7S6rCAriE4F0NkiR7pjECkj3cFGxCoTuTZb3iISnUpdbUOE4RzvjoWX5x\n" +
                        "57XefP7s9U9foC7JuKRU6jIfjjpEXsF/NuWsqYzzfH+NpIOy7mI9Gn2OeXWtiyxKRg3LqaEs0gJO\n" +
                        "eDssIow6sk51sIeHQ1FGYyAtbeE6lhdHLDpsrxZZlqaw4ZSPIUVVCFHBhdlAqXRTiwgzFYSL9sYV\n" +
                        "XhI01Cxu/9mffreIkoQqO72WQVJwLuqcQwQAjFBEBIVggGQjSe6/fmWzF0eb23d+9zuHFw+FONwf\n" +
                        "TVAPi0QQYqCyjUWj+ZPf+1G5Ut7fVSVUWGco9fOPUI/BWo+zrrJoW2qAEeuh1crWZGF11uqc5jqJ\n" +
                        "H/eH4wKtq7UuB7ZWGbVGrU+excNxcV8kaq0xjq+ebaZhaDzTGSFUsc4KFBELAfxUObYIVzlVoS8Y\n" +
                        "NUiK2o//7PsSUZNZa8Ko4fYegQ3obQq9j0mJnnUgT+1K8AgAwwfX/OVOpjDizVd3ZcxtmU1StEhJ\n" +
                        "Nje6HH243YYv2ghFRvjbfXjTJN4qEeNqFdMuaoubGz7ciUSULcG6HOPYmqpIWwyMhUTUFguPCwVS\n" +
                        "VRkNSE1Q1dlkvrujtP1+iUofVnPiz791G3qqfprmimYpETTrLDwLk0tgaKbBd1D+kmUQYQKwIAql\n" +
                        "CVQsaVJGJVDP9HSxxhYRAaWHijhUmgPaY0aAojKZ54RVUCQ1QQSjhGW9ebK+f3mAKrDcfryON0sE\n" +
                        "X3/14mptw9Mxffa4ffHKyrA7HKxKukl4OBSqN2v3C3xCajBwrvF4bHf3kAhbJUTUUCnz7sBl5Noq\n" +
                        "90ubSPcorWIBptAo3Lf57nqrlAiKCYCGmtTIMJXe0Cb6rgdIFaQqug/7teoyKfaHf/Y9QBIQauIk\n" +
                        "KEH0vpEuWX455s6AhARNNeKkgyQJBmC7BQ11qUq1bKKQhdOuTsclvLk3UQlCAJOgiUmUtY0fPFr2\n" +
                        "+zodG6okQ7jIxHH0ealBaBOV1hqQBbs2h6suNc3TcZZV5KHNy3TYNZujxezKkMMuDvuYxyHyeCR/\n" +
                        "8vFTE8DObRSmMOkBG0jrlCoRCgo0uguQdzWL90sT9gd/8rmqwr2jA+251hNGB71bmyBJJJDS+4OC\n" +
                        "vXh4MmeB4Xq1//oVp7jarg/T/OTZ+vD2OGyHqJ3jjaD4VFM2IJKJZrFtTp8+Pv70ZeMSIWoyLUe1\n" +
                        "aM1r5Jj23N62ZeJ6CLN62BG2BFwSdlOdo+5qkzL7QugxDe3hrdOXibtd2x+4300N8ryk43ZVNfRM\n" +
                        "TBRTCDodXc1SkgbyhKMUDJETXHiXvDv1NtH+6J99H0DUipSs8/JPzS6ICFGBnISL7DXu0ABJv0RS\n" +
                        "ERpojLS68ue741Rbjflh9qSaklcPNjGDUDX74mU0UXFDq609fx0Jul7FsmhWhx+razZ3srBNzhTV\n" +
                        "lwZj7Fs0DzmkR76flirOCPi8281x4DJP49V0nOsx3e85eXBYHZRffPgIIqrOk90R6IlTZAA7lVhF\n" +
                        "48QYxJlfdBGoroMR0fUPJGUYEBREExNvEBN16SaJJ/kREQ8HEUKFGnjyk70l1CO2UVfERCloSAK9\n" +
                        "302rbEMqdZooXNBsHI/OKrIh2SDCIHUzyLyft6MfZgTu9201pPZkqy/fmKjUxfJ9SyvmNM/kxniT\n" +
                        "AAAMSklEQVRHmd8cDlMpxT2mh5mxcPEqgpfVk+3Wj5blmyh5GewnvUgMkMinCgMvNqizSwMsalSc\n" +
                        "6ssIKHAuFJ7P60RgOKlhighVuKdOXe7FG0pEdNwGIHhqqeuOFGR0aE82OoP0sG9dLc8P2TE7Jcni\n" +
                        "nnOKpaUiOVu/VAEdk6aQlAIeMJ/mutTWFgw5wpY265B9PkRo80UKZjpRphpRq3ttsx3n43yMZZkm\n" +
                        "xLHpcpBjlVlxeHk/Qw+he1meP72KBFDUKJ2wp5JVJRlOdWZN0j2bUCCI3jh7Sf6dUw8n8HTmm6qZ\n" +
                        "e6Qi795I5x8A5CyQqvQIwaWr4tQlTSHO5esfXk9ZZpU5uH48NERVjJ+s923eNSyJuFG9KgvSJNFW\n" +
                        "w+R1qsts6qMdazu22jwF9i32i+6XnI+1TQvuH+6mw/NjvXuw9bS8merufndcxifHB/H17f1hPtZl\n" +
                        "t2u8GqbmB/G/u91ERA7T/I49TIGbRISJsvO4VVIggF6m75XX97NaeM9+2R/92fd7JsxEk6CR2svc\n" +
                        "CIEu7qbK6B9Tj+ixJp0CRI+mI0Ajw2uNCGQRq9hTBSD3x2pZ28PSWgxrnaqzxtW31sf7oygqyAjb\n" +
                        "JkOdPnrK+ZXmwbXM0wHCcHG2pabW3NHq+ltYWXt+NwcWuTrs/Xh/v2+yiLVpbpaOi98f21LGL2+0\n" +
                        "lqSqSARdRMSM2i2UJoGYqtBUTCwEgUj2rkz/TlV/3dLbf/pf/wCAiFLVvR85L8WhzlYKhoqCDiRE\n" +
                        "APBwiki0YE/Sk6STiACkblfhR1soFXnM0RpM1UCwKdX0sJ9ufvhs9/JBcyCZIGIo2N+lspFxs7vb\n" +
                        "h9TqiLxqjun4sDSvC5bj/vjQavPjEftjO8IejtUVmtYo6X7aVZGD2KtHOG7WIlDLajAzM1Mz2MnG\n" +
                        "J9VTn7XoqTMkqeJsgX6dqstzhVBE7I/+2fcL0flYxjBTj7BzqfaUz4mTGvo5VlLCoAEhQYZH6+FB\n" +
                        "0BnRnLG1qm6LBFSyTREhgEokRMpK2b8+iGLz2QfTwy7d5t3dnuCh1uPDXauVyhacGmubliVcZHf0\n" +
                        "JsN8nOrwZHf/sPdoHnmzbnMNifvDjFXeV315I3er1H9IEKqaUkopiYmoppSKaYDDkNib9gRi1s3M\n" +
                        "xf3xTJp93yGKSBKRRTxD67JozhFhgqCbZEgEIhq7g+ifj2hCMCmWUMA7mRWgRrQQTabMUmewPhrv\n" +
                        "h1a+nPMCMzlSXc243DwtqjFoOn5zePnVC5Pw4xGI2TMPsyZLastRozllstxsyDIvS9XVh6O/XOrb\n" +
                        "N5FWy/FIkQOr5KEdvSk09ItbzqN1aixESilypuq5ihkgQbFknemp3Qr36PdCVj8365Ah54Lhub/Y\n" +
                        "3UV0jtBSmkgvi2nK7g2iQvTOqBBBi2TuCGrS2gJBBXszmJk4VdFaC0E7UefdB+4+RX7OzZRzi9oo\n" +
                        "Mrx6y4yqUSWY03b3cL+93s77O9VjbT5sTBGCpS/eRrM25zIuwuOXdw5XWACzC6ARTdNm9sNC/GJd\n" +
                        "tZSkamauLOh43UhOXAYpIqroJPYQkda7B9CJpu8k6PwMUbi3Lgydwpw6HFDVpCbecOZ5q0gFQsQQ\n" +
                        "ZgqP6MMMIkQBVWZTR8ra2iyiLo1ofexISskBbTUEkXR64vvd9Gg3riyqI8RTtmVu4fj6zUMuxvu5\n" +
                        "tl6bszpLGrBUKQoK9ViHMcddi57rLWVxUIMpC/Lsjce3b9d8uDVFMoaqhPKsQyEqSJazmhlPbPgQ\n" +
                        "yAIkgAiRU2d2pyoDgITThQrYhY3UzX0CTLQJcgsfzDo3EkBY0gg1UbwDCojer+fQQEjTCA+1rFFh\n" +
                        "xpzpjuh3S1JKbF4MxwSOeG6T7/HJUlahD03CsFnjuFtIHB/qWIxRxfLkkDnUkisi3Np4XJqOyZuI\n" +
                        "SWpxrDGsylx1jvqwavfX0ITRRiSVrJZVcymmmpNZUqMkmChUhmSnngBTFRUTOxcpLklUVXV24nsK\n" +
                        "VEW+lMVIJhERKSSzIPpUglzoVSFNtPu4EM1J3L3PjxGKqlIlUZAR7pE0eUB1aVM2q4DAPSQNRRbJ\n" +
                        "epiLFtWZ9Uss09G/LcN1Sg/h6WqcCYqFsUVWgZlZsmV2hGpOs7eUCo/syV241GS/nJc3yblh2eQx\n" +
                        "yzCsUlLJqVscE2hOkkUEklKHV9qxu6WqMgIKp+QI9niRPE37IAmFIp/aC+SdySeZeqxM0jWdZumE\n" +
                        "Q/oxo1OjFUhLo3WiqjqdjSmLexiVSiUaaUkzV+4VsMacIR4TDSWPIvMsLNTeWftNqz/bzerx6TBs\n" +
                        "TamSHS3lIiEgqwRkyLJMotmmGiyozjecXzckyHqbU9IyIg9S8ohkSKqqeRyo1MHMREwCqkBKaiKq\n" +
                        "6ooiBKT2cR/v8R3PRv1E1AIgFgR4AounIClRQiV1knOne70PyXrPBkPmpAwmiVOTtzFamJn7ki1X\n" +
                        "tKQSzYv4smRyMTPSPWUzKJyWNOHYDuMqqUVdYpOkzfrL6suh1elECciCeqJ/naLZlJGKmDOtJGVs\n" +
                        "x1QGyYNoYcmjjcWMyZIlkVw0SUrJcuqNEimZWbJzPbloApE1opeH38vtvQPrGog+BQkmpXEGz+X6\n" +
                        "Pv7D/dw6ZX2ehdGD7/Ej1ESRxBtCI1ygAMREQnrLcTZpVbSoV5GhZsnUabDBTOaGENNGCyZd11o1\n" +
                        "p1zCG+rC2piaeKMs5i4RkXqm14TKpFHGFMnHIaUkyJKNw6hmFjmXZKqSkqWkVoZkOgyDiFAIPbVl\n" +
                        "magYSskkez0vJNmpPfNdBiYigJbMIozukiBgRCgMZyqRqSZBPhObhfRgNVX0MUw8xZ8RIQKkHBGl\n" +
                        "STu1ciiFAukzWEw9QFgzBZFXbhMXWFqpLjqnNDRGZJUmrXlpcPchogU8pC1wd6XQT1IQxgTSxJJm\n" +
                        "k5SSGs0kpRJZs0EsZ9WciwxQSaqaU6bRkon062FJ5Kxip5yUmYEehISe8PqlXaA0D9WAAmEiFidy\n" +
                        "+6muVVvrBl5atCS9szx1wHDKF4teVBIIBVuS7BaMZgGIqXkACk3q1bWoO12WsMihqtaUyUqLVsJp\n" +
                        "mnLy5tE7EpovzUdFhEYYKd4ABKCWBUoJukDTqBYpFRMXyybUkjWZ6SmaUUW2IobeC6mqlkQsUzRb\n" +
                        "bwI/B3qAK9K57Q2QiMVsIBwMMVEpIogGSh/RdB4qRIpIqrWqqr37/DnrcLbuF8eJnpN1bwqlKCVA\n" +
                        "CtTQa2mWTSIAV80R7h5mpoZIkZkB1DZrRPJQJ8nZfYVgqHuFKd0potTT5BWcRvyYZkiICDTZMIi4\n" +
                        "AGUYBdK7S1JOPdizJF0xQTVRqLqEijkMaqJBhEkRcQpEIcyABaoiBUWJYIiY6onPdjHt/VzSRdHO\n" +
                        "HFx1D7M+bebdECucHx3Qu4KigDIiWjWTPmpFcGGfmigYkovVxcHqhFoB4H6iUAznkWDGjDOL4L1Q\n" +
                        "7DzgICmgJg6qphNQNBMxVVWYZFUnw1gsAYCknOzkzU3ZQbW03s5MaQp1AJFEBTDpuqYelG7NnI3n\n" +
                        "4lZfGE5zHd7zBXEZ1iWnPrk4T3Dq018uVgyGywVZ4GpgGEI1WYsFzQ2ZWmtNAI0BUyBC4Cxqfp5A\n" +
                        "d4kwOtJBSLAJTC69emcclPMQcWo6BqAGSkp6IvGnnPucNzGjOkGkJCpJRaTTg9492CcvXPjb6gST\n" +
                        "ZmcjWoQw2POhlxX2KKfz36hmBVgu753aEb3nGoA+mAak99j7cvezJRExQaUqoyoMAseo2d1tKBLV\n" +
                        "yip564MKI+jeEiyfwlKJCIRAKZDmp0GF/f5lK2fre4pmVVWNp4KM0k7T3lxNHUxmxYxqBFWEog5R\n" +
                        "U+0Z0DPLHxBjdi4KE5Wgn3jwvAxPeo8I825eniURDV9EtZn1kk7zKafhPVVF7xADYJYAIT3i3PKi\n" +
                        "GtIstICzmQIhnq10ByStwVI2W+iWhuZO8WKFFEe1nFBPgwP6b1kykhDvFkDkZAqcTKoNkvtQHOmj\n" +
                        "FCL65BgYTFVkTBkATc8gUVU1Uai9p0YiQonmzYyqyvA49zEJ1aUJeKoon5lF2g1YREQkBtRMvBnA\n" +
                        "05C3LFRnnziindfd/z6n7i/z2EIVEVYl1FKGRrRu7EknmXMfrRdlSOHMDBV1MhAKS7Q5L2rWCYVC\n" +
                        "JNVKnCYFkCBCJUETBOIGCVU1y2ANpjws3gZL/XSkN8ykk3/HOclZI0ztksbDubkcgCZRJIRFVNJN\n" +
                        "inujhvJdJstPbGMl+f8BsG5WOTgl5KkAAAAASUVORK5CYII=\n"
            }
            val fruitName = when(i) {
                0 -> "Banana"
                1 -> "Mamão"
                2 -> "Maçã"
                3 -> "Abacaxi"
                else -> "Fruita $i"
            }

            val fruitBenefits = when(i) {
                0 -> "A banana é uma das frutas mais populares do Brasil e encontrada facilmente" +
                        " em todo território nacional. De origem asiática, ela se adaptou " +
                        "rapidamente ao clima do país e é um alimento bastante saudável, versátil" +
                        " e com preço acessível"
                1 -> "O mamão possui uma boa quantidade de nutrientes essenciais para a saúde. " +
                        "É rico em sais minerais, como Cálcio, Fósforo, Ferro, Sódio e Potássio, " +
                        "que participam na formação de ossos, dentes e sangue. O fruto também " +
                        "contém quantidades significativas de vitaminas A e C."
                2 -> "O maçã é também uma fruta rica em antioxidantes, como antocianinas e " +
                        "o ácido elágico, que conferem outros benefícios para a saúde, tias como " +
                        "combater o envelhecimento da pele, ajudar a prevenir doenças " +
                        "cardiovasculares, melhorar a capacidade mental, prevenir o câncer " +
                        "e ajudar a combater inflamações"
                3 -> "O abacaxi é também uma fruta rica em antioxidantes, como antocianinas e " +
                        "o ácido elágico, que conferem outros benefícios para a saúde, tias como " +
                        "combater o envelhecimento da pele, ajudar a prevenir doenças " +
                        "cardiovasculares, melhorar a capacidade mental, prevenir o câncer " +
                        "e ajudar a combater inflamações"
                else -> "A fruta é uma fonte de vitamina"
            }


            val item = Fruit(fruitImage, "$fruitName", fruitBenefits)
            fruitList.add(item)
        }
    }

}