package kchou97.dotify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import kchou97.dotify.databinding.ItemSongBinding

class SongListAdapter(private var listOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    var onSongClickListener:(song:Song) -> Unit = {_-> }

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

            songRoot.setOnClickListener {
                onSongClickListener(song)
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfSongs.size
    }

    fun updateSongList(newListOfSongs: List<Song>) {
        this.listOfSongs = newListOfSongs
        notifyDataSetChanged()
    }

    class SongViewHolder(val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root)
}