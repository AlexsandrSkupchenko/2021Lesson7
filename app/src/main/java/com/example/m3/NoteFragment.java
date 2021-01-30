package com.example.m3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class NoteFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private NoteData currentNoteData;

    private boolean isLandscape;

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    // вызывается после создания макета фрагмента, здесь мы проинициализируем список
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    // создаём список заметок на экране из массива в ресурсах
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.cities);

        // В этом цикле создаём элемент TextView,
        // заполняем его значениями,
        // и добавляем на экран.
        // Кроме того, создаём обработку касания на элемент
        for (int i = 0; i < notes.length; i++) {
            String note = notes[i];
            TextView tv = new TextView(getContext());
            tv.setText(note);
            tv.setTextSize(30);
            layoutView.addView(tv);
            final int fi = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentNoteData = new NoteData(fi, getResources().getStringArray(R.array.cities)[fi]);
                    showCoatOfArms(currentNoteData);


                }
            });
        }
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNoteData);
        super.onSaveInstanceState(outState);
    }


    // activity создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Определение, можно ли будет расположить рядом заметку в другом фрагменте
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentNoteData = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            // Если восстановить не удалось, то сделаем объект с первым индексом
            currentNoteData = new NoteData(0, getResources().getStringArray(R.array.cities)[0]);
        }



        // Если можно показать рядом заметку, то сделаем это
        if (isLandscape) {
            showLandCoatOfArms(currentNoteData);
        }
    }

    private void showCoatOfArms(NoteData currentNoteData) {
        if (isLandscape) {
            showLandCoatOfArms(currentNoteData);
        } else {
            showPortCoatOfArms(currentNoteData);
        }
    }


    // Показать заметку в ландшафтной ориентации
    private void showLandCoatOfArms(NoteData currentNoteData) {
        // Создаём новый фрагмент с текущей позицией для вывода заметки
        CoatNoteFragment detail = CoatNoteFragment.newInstance(currentNoteData);


        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.textViewNote, detail);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


    // Показать герб в портретной ориентации.
    private void showPortCoatOfArms(NoteData currentNoteData) {
        // Откроем вторую activity
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteActivity.class);
        // и передадим туда параметры
        intent.putExtra(CoatNoteFragment.ARG_NOTE, currentNoteData);
        startActivity(intent);
    }

}

