namespace Epione.Domain
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("epione.treatement")]
    public partial class treatement
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public treatement()
        {
            recommendations = new HashSet<recommendation>();
        }

        public int id { get; set; }

        [Column(TypeName = "date")]
        public DateTime? dateadded { get; set; }

        [StringLength(255)]
        public string description { get; set; }

        [StringLength(255)]
        public string illness { get; set; }

        [StringLength(255)]
        public string justif { get; set; }

        [StringLength(30)]
        public string result { get; set; }

        public int? doctor_id { get; set; }

        public int? medicalRecord_id { get; set; }

        public virtual medicalrecord medicalrecord { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<recommendation> recommendations { get; set; }

        public virtual user user { get; set; }
    }
}
