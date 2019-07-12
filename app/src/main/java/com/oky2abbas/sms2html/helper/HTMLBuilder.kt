package com.oky2abbas.sms2html.helper

import com.oky2abbas.sms2html.backup.BackupStruct

class HTMLBuilder {

    fun makeHTML(arraySMSModel: ArrayList<BackupStruct>): String {
        var htmlString = makeStart()

        arraySMSModel.forEach {
            htmlString += makeInfoContact(it.name!!, it.address!!)
            val name = it.name
            val address = it.address

            it.body.forEach {
                it.forEach {
                    htmlString += makeSMSList(name!!, address!!, it.key, it.value)
                }
            }
        }

        htmlString += makeEnd()
        return htmlString
    }

    private fun makeStart(): String {
        return "<html> <head> <style> body { margin: 0 auto; direction: rtl; max-width: 80%;" +
                " background-color: rgb(241, 241, 241); } .container-center { background-color: #302a2a;" +
                " padding: 10px; margin: 10px 0px 10px 0px; width: 100%; color: #ffffff; text-align: center;" +
                " float: center; direction: rtl; } .container-right { background-color: #f4ecbf; border-radius: 5px;" +
                " margin: 0px 5px 0px 5px; padding: 5px; text-align: right; width: fit-content; max-width: 300px;" +
                " direction: rtl; color: black; } .container-left { background-color: #3c55d0; border-radius: 5px;" +
                " margin: 0px 5px 0px 5px; padding: 5px; max-width: 300px; text-align: right; width: fit-content;" +
                " direction: rtl; color: #ffffff; } </style> </head>"
    }

    private fun makeEnd(): String {
        return "</body></html>"
    }

    private fun makeInfoContact(name: String, address: String): String {
        return "<div class=\"container-center\"></div>"
    }

    private fun makeSMSList(name: String, address: String, type: Int, text: String): String {
        var align = "left"
        if (type == 2) align = "right"

        return "<div class=\"container-$align\">\n" +
                "        <h6>$name $address</h6>\n" +
                "        <h5> $text </h5>\n" +
                "    </div>\n" +
                "    <p>"
    }
}