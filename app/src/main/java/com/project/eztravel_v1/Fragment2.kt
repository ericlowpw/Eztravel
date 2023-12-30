package com.project.eztravel_v1

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class Fragment2: Fragment() {

    private val REQUEST_PERMISSION_CODE = 101
    private lateinit var songListView: ListView
    private lateinit var songList: ArrayList<String>
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var imgBtnPause: ImageButton
    private lateinit var imgBtnFF: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment2, container, false)
        songListView = view.findViewById(R.id.musicListView)
        imgBtnPause = view.findViewById(R.id.imgBtnPause_)
        imgBtnFF = view.findViewById(R.id.imgBtnFF_)
        songList = ArrayList()

        imgBtnPause.setImageResource(android.R.drawable.ic_media_play)
        imgBtnPause.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer!!.pause()
                imgBtnPause.setImageResource(android.R.drawable.ic_media_play)
            } else {
                mediaPlayer?.start()
                imgBtnPause.setImageResource(android.R.drawable.ic_media_pause)
            }
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestStoragePermission()
        } else {
            displaySongList()
        }

        return view
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_PERMISSION_CODE
        )
    }

    @SuppressLint("Range")
    private fun displaySongList() {
        val musicUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor? = requireActivity().contentResolver.query(
            musicUri,
            null,
            null,
            null,
            null
        )

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val songTitle: String =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                songList.add(songTitle)
            } while (cursor.moveToNext())
        }

        cursor?.close()

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, songList)
        songListView.adapter = adapter

        songListView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedSongTitle: String = songList[position]
                val selectedSongUri: Uri? = getSongUri(selectedSongTitle)

                selectedSongUri?.let {
                    mediaPlayer?.stop()
                    mediaPlayer?.reset()
                    mediaPlayer = MediaPlayer.create(requireContext(), it)
                    mediaPlayer?.start()
                }
                imgBtnPause.setImageResource(android.R.drawable.ic_media_pause)
            }
    }

    @SuppressLint("Range")
    private fun getSongUri(songTitle: String): Uri? {
        val musicUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.TITLE + "=?"
        val selectionArgs = arrayOf(songTitle)
        val projection = arrayOf(MediaStore.Audio.Media._ID)

        val cursor = requireActivity().contentResolver.query(
            musicUri,
            projection,
            selection,
            selectionArgs,
            null
        )

        var songUri: Uri? = null

        if (cursor != null && cursor.moveToFirst()) {
            val songId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
            songUri = Uri.withAppendedPath(musicUri, "" + songId)
        }

        cursor?.close()
        return songUri
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMediaPlayer()
    }

    private fun stopMediaPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onPause() {
        super.onPause()
        stopMediaPlayer()
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displaySongList()
            }
        }
    }

    /*private lateinit var musicListView: ListView
    private lateinit var binding2 : Fragment2Binding
    private var currentSongIndex: Int = 0
    private var musicFiles: ArrayList<File> = ArrayList()
    private var mediaPlayer = MediaPlayer()
    private var imgBtnPause : ImageButton = binding2.imgBtnPause
    private var imgBtnFF : ImageButton = binding2.imgBtnFF*/

    /*override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Log.d("onCreate", "before binding")
        binding2 = Fragment2Binding.inflate(inflater, container, false)
        Log.d("onCreate", "after binding")
        return binding2.root
    }*/
    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("Fragment2", "onViewCreate")

        imgBtnPause.setImageResource(android.R.drawable.ic_media_play)
        imgBtnPause.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                imgBtnPause.setImageResource(android.R.drawable.ic_media_play)
            } else {
                mediaPlayer.start()
                imgBtnPause.setImageResource(android.R.drawable.ic_media_pause)
            }
        }
        imgBtnFF.setOnClickListener {
            if (currentSongIndex < musicFiles.size) {
                currentSongIndex++
            }
            playFile(musicFiles[currentSongIndex].toString())
        }
        musicListView = binding2.musicListView
        loadMusicFiles()
        displayMusicList()
    }*/

    /*private fun loadMusicFiles() {
        val musicFolder = File(Environment.getExternalStorageDirectory().absolutePath + "/Music")
        if (musicFolder.exists() && musicFolder.isDirectory) {
            val files = musicFolder.listFiles { file ->
                file.isFile && file.extension.equals("mp3", ignoreCase = true)
            }
            musicFiles.addAll(files!!)
        }
    }*/

    /*private fun displayMusicList() {
        val musicFilename = musicFiles.map { file -> file.name }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,musicFilename)
        musicListView.adapter = adapter

        musicListView.setOnItemClickListener { _, _, position, _ ->
            val selectedFile = musicFiles[position]

            //Toast.makeText(requestContext(), "listView item $position clicked : $selectedFile", Toast.LENGTH_LONG).show()
            playFile(selectedFile.toString())
            imgBtnPause.setImageResource(android.R.drawable.ic_media_pause)
        }
    }*/


    /*private fun playFile(filepath: String) {
        try {
            mediaPlayer.apply {
                reset()
                setDataSource(filepath)
                prepare()
                start()
            }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }*/

}