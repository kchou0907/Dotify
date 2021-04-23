package kchou97.dotify

import androidx.recyclerview.widget.DiffUtil
import com.ericchee.songdataprovider.Song

class DiffUtilCallback(private val oldList: List<Song>, private val newList: List<Song>): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSong = oldList[oldItemPosition]
        val newSong = newList[newItemPosition]

        return newSong == oldSong
    }
}