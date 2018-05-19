package com.melhorgrupo.printserverclient.arquivosEnviados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.melhorgrupo.printserverclient.db.DbGateway;

import java.util.ArrayList;
import java.util.List;

public class ArquivoEnviadoDAO {
    private final String TABLE_ARQUIVOS_ENVIADOS = "ArquivosEnviados";
    private DbGateway gw;

    public ArquivoEnviadoDAO(Context ctx) {
        gw = DbGateway.getInstance(ctx);
    }

    public List<ArquivoEnviado> retornarTodos() {
        List<ArquivoEnviado> arquivosEnviados = new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM " + TABLE_ARQUIVOS_ENVIADOS, null);

        while (cursor.moveToNext()) {
            arquivosEnviados.add(this.getArquivoEnviadoFromCursor(cursor));
        }

        cursor.close();
        return arquivosEnviados;
    }

    public ArquivoEnviado retornarUltimo() {
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM " + TABLE_ARQUIVOS_ENVIADOS + " ORDER BY ID DESC", null);
        if (cursor.moveToFirst()) {
            ArquivoEnviado arquivoEnviado = this.getArquivoEnviadoFromCursor(cursor);
            cursor.close();
            return arquivoEnviado;
        }

        return null;
    }

    public boolean salvar(String nome, String enviadoEm, String ultimaImpressao) {
        return salvar(0, nome, enviadoEm, ultimaImpressao);
    }

    public boolean salvar(int id, String nome, String enviadoEm, String ultimaImpressao) {
        ContentValues cv = new ContentValues();
        cv.put("Nome", nome);
        cv.put("EnviadoEm", enviadoEm);
        cv.put("UltimaImpressao", ultimaImpressao);

        if (id > 0) {
            return gw.getDatabase().update(TABLE_ARQUIVOS_ENVIADOS, cv, "ID=?", new String[]{id + ""}) > 0;
        }

        return gw.getDatabase().insert(TABLE_ARQUIVOS_ENVIADOS, null, cv) > 0;
    }

    public boolean excluir(int id) {
        return gw.getDatabase().delete(TABLE_ARQUIVOS_ENVIADOS, "ID=?", new String[]{ id + "" }) > 0;
    }

    private ArquivoEnviado getArquivoEnviadoFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("ID"));
        String nome = cursor.getString(cursor.getColumnIndex("Nome"));
        String enviadoEm = cursor.getString(cursor.getColumnIndex("EnviadoEm"));
        String ultimaImpressao = cursor.getString(cursor.getColumnIndex("UltimaImpressao"));

        return new ArquivoEnviado(id, nome, enviadoEm, ultimaImpressao);
    }
}
