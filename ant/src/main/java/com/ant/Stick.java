package com.ant;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Stick extends RenderObject {
    private Integer leftEnd;
    private Integer rightEnd;

    @Override
    public void update() {
        this.render();
    }

    private void render(){

    }

}
