package consolegui;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Vector;

import org.junit.Test;

public class SpriteBuildServiceTest {
    @Test
    public void SpriteBuildServiceのテスト(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                var tmp = SpriteBuildService.BuildModel("./testdata/consolegui/Character1.txt", i, j);
                String tmp2 = "";
                for(int k=0;k<tmp.size();k++){
                    for( int l=0;l<tmp.get(k).size();l++){
                        tmp2 += tmp.get(k).get(l).word;
                    }
                }
                assertEquals(tmp2, "+");
            }
        }
        
    }
    @Test
    public void SpriteBuildServiceのテスト2(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                var tmp = SpriteBuildService.BuildModel("./testdata/consolegui/Character2.txt", i, j);
                String tmp2 = "";
                for(int k=0;k<tmp.size();k++){
                    for( int l=0;l<tmp.get(k).size();l++){
                        tmp2 += tmp.get(k).get(l).word;
                    }
                    tmp2+='\n';
                }
                assertEquals(tmp2,  "***\n***\n**+\n");
            }
        }
    }
}
