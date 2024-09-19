import java.awt.List
import kotlin.String

fun defTotal(defPerso: Int, armure: Int, rarete: Int): Int{
    return defPerso+armure+rarete
}

fun lanceDes(nbLances: Int, nbFaces: Int): Int{
    return (nbLances .. nbFaces*nbLances).random()
    }

fun degatArme(nbLances: Int,nbFaces: Int,qualite: Int,activCrit: Int,multiplicateurCrit: Int): Int{
    var des = lanceDes(nbLances, nbFaces)
    if (des >= activCrit){
        des*=multiplicateurCrit
    }
    des+=qualite
    if (des<0){
        des=0
    }
    return des
}

fun attaque(pv: Int, def: Int, atk: Int, nomAtk: String, nomCible: String){
    var degat = atk-def
    if (degat<0) {
        degat = 0
    }
    var pointVie = pv-degat
    if (pointVie<0){
        pointVie=0
    }
    println("$nomAtk attaque $nomCible pour $degat point de dégâts")
}
fun boirePotion(nomCible: String, nbPv: Int,nbPvMax: Int,puisPot: Int){
    var expv = nbPv
    expv += puisPot
    if (expv>nbPvMax){
        expv=nbPvMax
    }
    val pvGagne = expv - nbPv
    println("$nomCible boit une potion est récupere $pvGagne PV")
}

fun bdf(nomAtk: String,nomCible: String,atk: Int,def: Int,pv: Int){
    val degat = lanceDes(atk/3,6)
    var nbPv=pv-(degat-def)
    if (nbPv<0){
        nbPv=0
    }
    val pvPerdu = pv-nbPv
    println("$nomAtk lance une boule de feu et inflige $pvPerdu points de dégâts à $nomCible.")
}

fun missileMagique(nomAtk: String,nomCible: String,atk: Int,def: Int,pv: Int){
    var degat = 0
    var nbPv = pv
    var nbmissile = 0
    while (nbmissile < atk/2) {
        degat = lanceDes(1,6)
        degat+=def
        println("$nomAtk lance une missile magique et inflige $degat points de dégâts à $nomCible.")
        nbPv-=degat
        if (nbPv<=0){
            nbPv = 0
            break
        }
        nbmissile+=1
    }
}
fun soins(nomAtk: String,nomCible: String,atk: Int,pv: Int,pvMax: Int, boolean: Boolean){
    var soins = atk/2
    var nbPv = pv
    if (boolean == true){
        nbPv -= soins
    }
    else {
        nbPv += soins
        if (nbPv > pvMax){
            nbPv = pvMax
        }
    }
    var x= pv - nbPv
    if (nbPv < pv){
        println("$nomAtk a blesse $nomCible de $x pv")
    }
    else {
        x*=-1
        println("$nomAtk a soigne $nomCible de $x pv")
    }
}

fun afficheInventaire(nomAtk: String,inventaire: MutableList<String>){
    println("Inventaire de $nomAtk")
    for (i in inventaire.indices){
        println("$i => ${inventaire[i]} ")
    }
}

fun choisirItem(nomAtk: String,inventaire: MutableList<String>){
    var inv = afficheInventaire(nomAtk,inventaire)
    println("veuillez selectionner un item")
    var res = readln().toInt()
    var a=0
    while (a<1){
        if (res in inventaire.indices) {
            a=1
            println("$nomAtk utilise ${inventaire[res]}")
        }
        else{
            inv = afficheInventaire(nomAtk,inventaire)
            println("veuillez selectionner un item")
             res = readln().toInt()
        }
    }
}

fun tourJoueur(pv: Int, def: Int, atk: Int, nomAtk: String, nomCible: String,pvMax: Int, boolean: Boolean,inventaire: MutableList<String>){
    println("Qu'elle action veut tu effectuer : 1 = Attaquer - 2 = Boule de feu - 3 = Missile magique - 4 = Soins - 5 = Utiliser un item ")
    var res = readln().toInt()
    var a=0
    while (a<1){
        if (res == 1){
            a=1
            attaque(pv,def,atk,nomAtk,nomCible)
        }
        else if (res == 2){
            a=1
            bdf(nomAtk,nomCible,atk,def,pv)
        }
        else if (res == 3){
            a=1
            missileMagique(nomAtk,nomCible,atk,def,pv)
        }
        else if (res == 4){
            a=1
            soins(nomAtk,nomCible,atk,pv,pvMax, boolean)
        }
        else if (res == 5){
            a=1
            choisirItem(nomAtk,inventaire)
        }
        else{
            println("Qu'elle action veut tu effectuer : 1 = Attaquer - 2 = Boule de feu - 3 = Missile magique - 4 = Soins - 5 = Utiliser un item ")
            res = readln().toInt()
        }
    }
}

fun tourOrdinateur(pv: Int, def: Int, atk: Int, nomAtk: String, nomCible: String,pvMax: Int, boolean: Boolean){
    var res=lanceDes(1,30)
    if (res<15){
        attaque(pv,def,atk,nomAtk,nomCible)
    }
    else if (res < 20){
        bdf(nomAtk,nomCible,atk,def,pv)
    }
    else if (res < 25){
        missileMagique(nomAtk,nomCible,atk,def,pv)
    }
    else if (res < 30){
        soins(nomAtk,nomCible,atk,pv,pvMax, boolean)
    }
}
fun tourCombat(pv: Int, def: Int, atk: Int, nomAtk: String, nomCible: String,pvMax: Int, boolean: Boolean,inventaire: MutableList<String>){
    println("debut tour")
    tourJoueur(pv, def, atk, nomAtk, nomCible,pvMax, boolean,inventaire)
    tourOrdinateur(pv, def, atk, nomCible, nomAtk,pvMax, boolean)
    println("fin du tour")
}

fun main(){
    //println(defTotal(3,2,1))
    //println(lanceDes(2,6))
    //println(degatArme(1,6,2,6,2))
    //attaque(150,defTotal(3,2,1),degatArme(1,6,2,6,2),"jaja","isma")
    //boirePotion("jaja",8,20,6)
    //bdf("jaja","francois",15,7,150)
    //missileMagique("jaja","zyad",20,4,100)
    //soins("jaja","hugo",20,150,200,true)
    //afficheInventaire("jaja",mutableListOf("obj1","obj2","obj3","obj4"))
    //choisirItem("jaja",mutableListOf("obj1","obj2","obj3","obj4"))
    //tourJoueur(200,10,20,"jaja","youssef",250,true,mutableListOf("obj1","obj2","obj3","obj4"))
    //tourOrdinateur(200,10,20,"jaja","youssef",250,true)
    //tourCombat(200,10,20,"jaja","youssef",250,true,mutableListOf("obj1","obj2","obj3","obj4"))
}