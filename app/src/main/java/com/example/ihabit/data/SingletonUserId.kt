package com.example.ihabit.data

class SingletonUserId private constructor(){

    private var userId=0


    companion object{
      var sington=SingletonUserId()


        fun getInstance(): SingletonUserId? {
            if (sington == null) {
                synchronized(SingletonUserId::class.java) {
                    if (sington == null) {
                        sington = SingletonUserId()
                    }
                }
            }
            return sington
        }
    }

    fun getId():Int{
        return this.userId
    }
    fun setID(id:Int){
        this.userId=id
    }

}