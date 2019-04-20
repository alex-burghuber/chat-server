package messages;

public class GroupMessage extends Message {

    private String name;

    public GroupMessage(String type, String name) {
        super(type);
        this.name = name;
    }

    @Override
    public String getType() {
        return super.getType();
    }

    @Override
    public void setType(String type) {
        super.setType(type);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
