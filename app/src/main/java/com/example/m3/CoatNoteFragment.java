package com.example.m3;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CoatNoteFragment extends Fragment {

    public static final String ARG_NOTE = "note";
    private NoteData noteData;

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы.
    public static CoatNoteFragment newInstance(NoteData noteData) {
        CoatNoteFragment f = new CoatNoteFragment();    // создание

        // Передача параметра
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, noteData);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            noteData = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_coat_of_arms, container, false);
        // найти в контейнере элемент-вывода
        AppCompatTextView textCoatOfArms = view.findViewById(R.id.textViewNote);
        AppCompatTextView textDate = view.findViewById(R.id.textViewDate);
        // Получить из ресурсов массив указателей на текст
        TypedArray note = getResources().obtainTypedArray(R.array.indexNote);
        // Выбрать по индексу подходящий
        textCoatOfArms.setText(note.getResourceId(noteData.getNoteIndex(),-1));
        // Установить название
        TextView noteNameView = view.findViewById(R.id.textViewTitle);
        noteNameView.setText(noteData.getNoteName());
        return view;
    }
}

