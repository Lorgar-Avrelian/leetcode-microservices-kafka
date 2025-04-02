package lorgar.avrelian.base.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;
import java.util.Objects;
@Schema(title = "Test values", description = "Map of Test values by Expected results")
public class TestValues<M, T> {
    @Schema(title = "Test map", description = "Map of Test values by Expected results")
    private Map<M, T> testMap;

    public TestValues() {
    }

    public TestValues(Map<M, T> testMap) {
        this.testMap = testMap;
    }

    public Map<M, T> getTestMap() {
        return testMap;
    }

    public void setTestMap(Map<M, T> testMap) {
        this.testMap = testMap;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TestValues<?, ?> that = (TestValues<?, ?>) o;
        return Objects.equals(testMap, that.testMap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(testMap);
    }

    @Override
    public String toString() {
        return "TestValues{" +
                "testMap=" + testMap +
                '}';
    }
}
