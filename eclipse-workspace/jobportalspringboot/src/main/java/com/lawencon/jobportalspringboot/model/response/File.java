package com.lawencon.jobportalspringboot.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File {
    String fileName;
    String fileExt;
    byte[] data;
}
