package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*W odpowiedzi na zainteresowanie naszą ofertą pracy chcielibyśmy zaprosić Panią do pierwszego etapu rekrutacji na stanowisko Junior Java Developer w firmie Horus.
Poniżej przekazujemy zadanie z prośbą o analizę poniższego kodu i zaimplementowanie metod findBlockByColor, findBlocksByMaterial, count w klasie Wall -
najchętniej unikając powielania kodu i umieszczając całą logikę w klasie Wall.
Z uwzględnieniem w analizie i implementacji interfejsu CompositeBlock! */
interface Structure {
    // zwraca dowolny element o podanym kolorze
    Optional<Block> findBlockByColor(String color);

    // zwraca wszystkie elementy z danego materiału
    List<Block> findBlocksByMaterial(String material);

    //zwraca liczbę wszystkich elementów tworzących strukturę
    int count();
}

public class Wall implements Structure {
    private List<Block> blocks;

    @Override
    public Optional<Block> findBlockByColor(String color) {

        for (Block block : blocks) {
            if(block.getColor().equals(color))
                return Optional.of(block);
            if (block instanceof CompositeBlock) {
                Optional<Block> compositeBlockResult = ((CompositeBlock) block).getBlocks().stream()
                        .filter(b -> b.getColor().equals(color))
                        .findFirst();
                if (compositeBlockResult.isPresent()) {
                    return compositeBlockResult;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> oneMaterial = new ArrayList<>();

        for (Block block : blocks) {
            if(block.getMaterial().equals(material))
                oneMaterial.add(block);

            if (block instanceof CompositeBlock) {
                List<Block> compositeBlockResults = ((CompositeBlock) block).getBlocks().stream()
                        .filter(b -> b.getMaterial().equals(material))
                        .toList();
                oneMaterial.addAll(compositeBlockResults);
            }
        }

        return oneMaterial;
    }

    @Override
    public int count() {
        int count = blocks.size();

        for (Block block : blocks) {
            if (block instanceof CompositeBlock) {
                count += ((CompositeBlock) block).getBlocks().size() - 1;
            }
        }
        return count;
    }

}

interface Block {
    String getColor();
    String getMaterial();
}

interface CompositeBlock extends Block {
    List<Block> getBlocks();
}