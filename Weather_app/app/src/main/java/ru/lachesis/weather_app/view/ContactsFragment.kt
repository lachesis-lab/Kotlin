package ru.lachesis.weather_app.view

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.lachesis.weather_app.R
import ru.lachesis.weather_app.databinding.ContactsFragmentBinding

private const val REQUEST_CODE = 442

class ContactsFragment : Fragment() {

    private var _binding: ContactsFragmentBinding? = null
    private val binding: ContactsFragmentBinding get() = _binding!!

    companion object {
        fun newInstance() = ContactsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ContactsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermissions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkPermissions() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_CONTACTS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    getContacts()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    AlertDialog.Builder(it)
                        .setTitle("Доступ к контактам")
                        .setMessage("Для правильной работы приложения необходим доступ ${Manifest.permission.READ_CONTACTS}")
                        .setPositiveButton(
                            "Предоставить доступ"
                        ) { dialog, which ->
                            requestPermissions(
                                arrayOf(Manifest.permission.READ_CONTACTS),
                                REQUEST_CODE
                            )
                        }
                        .setNegativeButton("Отставить") { dialog, _ -> dialog.dismiss() }
                        .create()
                        .show()
                }
                else -> {
                    requestPermissions(
                        arrayOf(Manifest.permission.READ_CONTACTS),
                        REQUEST_CODE
                    )

                }
            }
        }

    }


    private fun getContacts() {
        context?.let { it ->
            val contentResolver: ContentResolver = it.contentResolver
            val cursorContacts: Cursor? = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                ),
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )
            cursorContacts?.let {
                it.moveToFirst()
                do {
                    val string = "${it.getString(0)} ${it.getString(1)}"
                    binding.contactsContainer.addView(TextView(context).apply {
                        text = string
                        textSize = resources.getDimension(R.dimen.contact_text_size)
                    })
                    it.moveToNext()

                } while (!cursorContacts.isAfterLast)
            }
        }
    }
}