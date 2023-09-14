package org.demo.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.Part;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.EntityPart;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/multipart")
@RequestScoped
public class MultipartResource {

    java.nio.file.Path uploadedPath;

    @PostConstruct
    public void init() {
        log.info("postConstruct");
        try {
            uploadedPath = Paths.get("/home/revit/tmp/uploads");
            if (!Files.exists(uploadedPath)) {
                Files.createDirectory(uploadedPath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    
    @Path("sample")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public String echo(String requestBody) throws Exception {
        return requestBody;
    }

    @Path("simple")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormParam("name") String name,
                               @FormParam("part") EntityPart part) {
        log.info("DDD");
        log.info(part.toString());
        
        try {
            Files.copy(
                    part.getContent(),
                    Paths.get(uploadedPath.toString(), part.getFileName().orElse(generateFileName(UUID.randomUUID().toString(), mediaTypeToFileExtension(part.getMediaType())))),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return Response.ok().build();
    }

    @Path("list")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadMultiFiles(List<EntityPart> parts) {
 
        parts.forEach(
                part -> {
                    try {
                        Files.copy(
                                part.getContent(),
                                Paths.get(uploadedPath.toString(), part.getFileName().orElse(generateFileName(UUID.randomUUID().toString(), mediaTypeToFileExtension(part.getMediaType())))),
                                StandardCopyOption.REPLACE_EXISTING
                        );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        return Response.ok().build();
    }

    @GET
    public List<EntityPart> getFiles() throws IOException {
        List<EntityPart> parts = new ArrayList<>();
        parts.add(EntityPart.withName("abd")
                .fileName("abc.text").content("this is a text content")
                .mediaType(MediaType.TEXT_PLAIN_TYPE)
                .build()
        );
        try (var files = Files.list(uploadedPath)) {
            var partsInUploaded = files
                    .map(path -> {
                                var file = path.toFile();
                                log.info("found uploaded file: {0}", file.getName());
                                try {
                                    return EntityPart.withName(file.getName())
                                            .fileName(file.getName())
                                            .content(new FileInputStream(file))
                                            .mediaType(fileExtensionToMediaType(getFileExt(file.getName())))
                                            .build();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    )
                    .toList();
            parts.addAll(partsInUploaded);
        }
        return parts;
    }

    private String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private MediaType fileExtensionToMediaType(String extension) {
        return switch (extension.toLowerCase()) {
            case "txt" -> MediaType.TEXT_PLAIN_TYPE;
            case "svg" -> MediaType.APPLICATION_SVG_XML_TYPE;
            default -> MediaType.APPLICATION_OCTET_STREAM_TYPE;
        };
    }

    private String generateFileName(String fileName, String extension) {
        return fileName + "." + extension;
    }

    private String mediaTypeToFileExtension(MediaType mediaType) {
        return switch (mediaType.toString()) {
            case "text/plain" -> "txt";
            case "application/svg+xml" -> "svg";
            default -> "bin";
        };
    }
}
