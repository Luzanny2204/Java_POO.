package usercrud.model;

public class Official {
    private int id;
    private String nome;
    private String cargo;
    private double salario;
    private String dataAdmisao;
    private int cargosAdicionales; // Nuevo atributo para los cargos adicionales

    public Official(int id, String nome, String cargo, double salario, String dataAdmisao) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.salario = salario;
        this.dataAdmisao = dataAdmisao;
        this.cargosAdicionales = 0; // Inicialmente establecido en 0
    }

    // Getter y Setter para cargosAdicionales
    public int getCargosAdicionales() {
        return cargosAdicionales;
    }

    public void setCargosAdicionales(int cargosAdicionales) {
        this.cargosAdicionales = cargosAdicionales;
    }

    // Resto de los getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getDataAdmisao() {
        return dataAdmisao;
    }

    public void setDataAdmisao(String dataAdmisao) {
        this.dataAdmisao = dataAdmisao;
    }

    @Override
    public String toString() {
        return nome;
    }
}
