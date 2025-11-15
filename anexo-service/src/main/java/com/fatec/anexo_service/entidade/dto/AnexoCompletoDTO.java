package com.fatec.anexo_service.entidade.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO completo com URLs diretas - não precisa mais de IDs!
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnexoCompletoDTO {

    // Informações básicas
    private String id;
    private String fileName;
    private String fileSize;
    private String tipo;
    private LocalDateTime dataUpload;

    // URLs diretas - prontas para usar!
    private String urlDownload;
    private String urlPreview;
    private String urlExcluir;

    /**
     * Formata tamanho do arquivo
     */
    public static String formatarTamanho(Long bytes) {
        if (bytes == null || bytes == 0) {
            return "0 B";
        }

        double kb = bytes / 1024.0;
        if (kb < 1024) {
            return String.format("%.1f KB", kb);
        }

        double mb = kb / 1024.0;
        return String.format("%.1f MB", mb);
    }
}
