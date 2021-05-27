package kchou97.dotify.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import kchou97.dotify.DiffUtilCallback
import kchou97.dotify.databinding.ItemSongBinding

class SongListAdapter(private var listOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    var onSongClickListener:(song:Song) -> Unit = {_-> }
    var onSongLongClickListener:(song:Song) -> Unit = {_-> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context))

        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = listOfSongs[position]

        with(holder.binding) {
            SongName.text = song.title
            SongArtist.text = song.artist
            AlbumPic.setImageResource(song.smallImageID)

            // when clicked, runs the lambda that returns the current song
            songRoot.setOnClickListener {
                onSongClickListener(song)
            }

            // removes song from the list if long pressed
            songRoot.setOnLongClickListener{
                onSongLongClickListener(song)
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfSongs.size
    }

    /*
    * Updates the song list
    * @Param newListOfSongs List<Song> - the new song list that should be displayed
    * */
    fun updateSongList(newListOfSongs: List<Song>) {
        val callback = DiffUtilCallback(oldList = listOfSongs, newList = newListOfSongs)
        val diffRes = DiffUtil.calculateDiff(callback)
        diffRes.dispatchUpdatesTo(this)

        this.listOfSongs = newListOfSongs
    }

    /*
    * Removes the song from the list
    * @Param song Song - the song that you want to remove from the list
    * */
    fun removeFromList(song: Song) {
        val newList = listOfSongs.toMutableList().apply {remove(song)}

        updateSongList(newList)
    }

    class SongViewHolder(val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root)
}

